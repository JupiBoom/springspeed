package com.yasinyuan.testspring.service.impl;

import com.yasinyuan.testspring.dao.FunnelConfigRepository;
import com.yasinyuan.testspring.dao.FunnelConversionResultRepository;
import com.yasinyuan.testspring.dao.FunnelStepRepository;
import com.yasinyuan.testspring.dao.UserBehaviorRepository;
import com.yasinyuan.testspring.model.FunnelConfig;
import com.yasinyuan.testspring.model.FunnelConversionResult;
import com.yasinyuan.testspring.model.FunnelStep;
import com.yasinyuan.testspring.model.UserBehavior;
import com.yasinyuan.testspring.service.FunnelAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FunnelAnalysisServiceImpl implements FunnelAnalysisService {

    @Autowired
    private FunnelConfigRepository funnelConfigRepository;

    @Autowired
    private FunnelStepRepository funnelStepRepository;

    @Autowired
    private FunnelConversionResultRepository funnelConversionResultRepository;

    @Autowired
    private UserBehaviorRepository userBehaviorRepository;

    @Override
    @Transactional
    public FunnelConfig saveFunnelConfig(FunnelConfig funnelConfig, List<FunnelStep> funnelSteps) {
        funnelConfig.setCreateTime(new Date());
        funnelConfig.setUpdateTime(new Date());
        FunnelConfig savedConfig = funnelConfigRepository.save(funnelConfig);

        for (FunnelStep step : funnelSteps) {
            step.setFunnelId(savedConfig.getId());
            step.setCreateTime(new Date());
            step.setUpdateTime(new Date());
        }
        funnelStepRepository.saveAll(funnelSteps);

        return savedConfig;
    }

    @Override
    @Transactional
    public FunnelConfig updateFunnelConfig(FunnelConfig funnelConfig, List<FunnelStep> funnelSteps) {
        funnelConfig.setUpdateTime(new Date());
        FunnelConfig updatedConfig = funnelConfigRepository.save(funnelConfig);

        // 删除旧步骤
        funnelStepRepository.deleteByFunnelId(updatedConfig.getId());

        // 保存新步骤
        for (FunnelStep step : funnelSteps) {
            step.setFunnelId(updatedConfig.getId());
            step.setCreateTime(new Date());
            step.setUpdateTime(new Date());
        }
        funnelStepRepository.saveAll(funnelSteps);

        return updatedConfig;
    }

    @Override
    @Transactional
    public void deleteFunnelConfig(Long funnelId) {
        funnelStepRepository.deleteByFunnelId(funnelId);
        funnelConfigRepository.deleteById(funnelId);
    }

    @Override
    public List<FunnelConfig> listEnabledFunnelConfigs() {
        return funnelConfigRepository.findByEnabledTrue();
    }

    @Override
    public FunnelConfig getFunnelConfigById(Long funnelId) {
        return funnelConfigRepository.findById(funnelId).orElse(null);
    }

    @Override
    @Transactional
    public List<FunnelConversionResult> calculateFunnelConversion(Long funnelId, Date startDate, Date endDate) {
        FunnelConfig funnelConfig = getFunnelConfigById(funnelId);
        if (funnelConfig == null) {
            throw new RuntimeException("Funnel config not found");
        }

        List<FunnelStep> funnelSteps = funnelStepRepository.findByFunnelIdOrderByStepAsc(funnelId);
        if (funnelSteps.isEmpty()) {
            throw new RuntimeException("Funnel has no steps");
        }

        List<FunnelConversionResult> results = new ArrayList<>();
        Long previousUserCount = 0L;

        for (int i = 0; i < funnelSteps.size(); i++) {
            FunnelStep step = funnelSteps.get(i);
            Set<String> userIds = getUserIdsForStep(step, startDate, endDate);
            Long currentUserCount = (long) userIds.size();

            FunnelConversionResult result = new FunnelConversionResult();
            result.setFunnelId(funnelId);
            result.setFunnelName(funnelConfig.getFunnelName());
            result.setStepOrder(step.getStepOrder());
            result.setStepName(step.getStepName());
            result.setUserCount(currentUserCount);
            result.setDate(startDate);

            if (i == 0) {
                result.setConversionRate(100.00);
            } else {
                double conversionRate = previousUserCount > 0 ? (currentUserCount * 100.00) / previousUserCount : 0;
                result.setConversionRate(conversionRate);
            }

            results.add(result);
            previousUserCount = currentUserCount;
        }

        funnelConversionResultRepository.saveAll(results);
        return results;
    }

    private Set<String> getUserIdsForStep(FunnelStep step, Date startDate, Date endDate) {
        List<UserBehavior> behaviors = userBehaviorRepository.findByEventTypeAndCreateTimeBetween(
                step.getEventType(), startDate, endDate);

        if (step.getPagePath() != null && !step.getPagePath().isEmpty()) {
            behaviors = behaviors.stream()
                    .filter(b -> step.getPagePath().equals(b.getPagePath()))
                    .collect(Collectors.toList());
        }

        return behaviors.stream()
                .map(UserBehavior::getUserId)
                .collect(Collectors.toSet());
    }

    @Override
    public List<FunnelConversionResult> getFunnelConversionResult(Long funnelId, Date date) {
        return funnelConversionResultRepository.findByFunnelIdAndDate(funnelId, date);
    }

    @Override
    public Map<String, Object> getLostUsers(Long funnelId, Integer fromStep, Date startDate, Date endDate) {
        FunnelConfig funnelConfig = getFunnelConfigById(funnelId);
        if (funnelConfig == null) {
            throw new RuntimeException("Funnel config not found");
        }

        List<FunnelStep> funnelSteps = funnelStepRepository.findByFunnelIdOrderByStepAsc(funnelId);
        if (fromStep >= funnelSteps.size() - 1) {
            throw new RuntimeException("Invalid from step, no next step exists");
        }

        FunnelStep currentStep = funnelSteps.get(fromStep);
        FunnelStep nextStep = funnelSteps.get(fromStep + 1);

        Set<String> currentStepUsers = getUserIdsForStep(currentStep, startDate, endDate);
        Set<String> nextStepUsers = getUserIdsForStep(nextStep, startDate, endDate);

        Set<String> lostUsers = currentStepUsers.stream()
                .filter(userId -> !nextStepUsers.contains(userId))
                .collect(Collectors.toSet());

        Map<String, Object> result = new HashMap<>();
        result.put("currentStepName", currentStep.getStepName());
        result.put("nextStepName", nextStep.getStepName());
        result.put("totalUsers", currentStepUsers.size());
        result.put("convertedUsers", nextStepUsers.size());
        result.put("lostUsersCount", lostUsers.size());
        result.put("lostUsers", lostUsers);
        result.put("lossRate", currentStepUsers.size() > 0 ? (lostUsers.size() * 100.00) / currentStepUsers.size() : 0);

        return result;
    }
}
