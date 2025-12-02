package com.ecommerce.service.impl;

import com.ecommerce.entity.Funnel;
import com.ecommerce.entity.FunnelAnalysisResult;
import com.ecommerce.repository.FunnelRepository;
import com.ecommerce.repository.FunnelAnalysisResultRepository;
import com.ecommerce.service.FunnelAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FunnelAnalysisServiceImpl implements FunnelAnalysisService {

    @Autowired
    private FunnelRepository funnelRepository;

    @Autowired
    private FunnelAnalysisResultRepository funnelAnalysisResultRepository;

    @Override
    public Funnel createFunnel(String funnelName, String description, String funnelSteps, String createdBy) {
        Funnel funnel = new Funnel();
        funnel.setFunnelName(funnelName);
        funnel.setDescription(description);
        funnel.setFunnelSteps(funnelSteps);
        funnel.setCreatedBy(createdBy);
        funnel.setIsActive(true);
        funnel.setCreatedTime(new Date());
        funnel.setLastUpdatedTime(new Date());
        
        return funnelRepository.save(funnel);
    }

    @Override
    public Funnel updateFunnel(Long funnelId, String funnelName, String description, String funnelSteps, Boolean isActive) {
        Funnel funnel = funnelRepository.findById(funnelId).orElseThrow(() -> new RuntimeException("Funnel not found"));
        
        if (funnelName != null) funnel.setFunnelName(funnelName);
        if (description != null) funnel.setDescription(description);
        if (funnelSteps != null) funnel.setFunnelSteps(funnelSteps);
        if (isActive != null) funnel.setIsActive(isActive);
        
        funnel.setLastUpdatedTime(new Date());
        
        return funnelRepository.save(funnel);
    }

    @Override
    public void deleteFunnel(Long funnelId) {
        funnelRepository.deleteById(funnelId);
    }

    @Override
    public List<Funnel> getAllFunnels() {
        return funnelRepository.findAll();
    }

    @Override
    public List<Funnel> getActiveFunnels() {
        return funnelRepository.findByIsActiveTrue();
    }

    @Override
    public Funnel getFunnelById(Long funnelId) {
        return funnelRepository.findById(funnelId).orElseThrow(() -> new RuntimeException("Funnel not found"));
    }

    @Override
    public List<FunnelAnalysisResult> analyzeFunnelConversion(Long funnelId, Date analysisDate) {
        Funnel funnel = getFunnelById(funnelId);
        String[] steps = funnel.getFunnelSteps().split(",");
        
        List<FunnelAnalysisResult> results = new ArrayList<>();
        Integer previousUserCount = null;
        Integer firstUserCount = null;
        
        for (int i = 0; i < steps.length; i++) {
            String stepName = steps[i].trim();
            
            // 这里应该根据实际的业务逻辑查询每个步骤的用户数量
            // 暂时使用模拟数据
            Integer userCount = simulateUserCountForStep(funnelId, stepName, analysisDate);
            
            FunnelAnalysisResult result = new FunnelAnalysisResult();
            result.setFunnelId(funnelId);
            result.setStepIndex(i);
            result.setStepName(stepName);
            result.setUserCount(userCount);
            result.setAnalysisDate(analysisDate);
            result.setCreatedTime(new Date());
            
            // 计算转化率
            if (previousUserCount != null && previousUserCount > 0) {
                double conversionRate = (double) userCount / previousUserCount;
                result.setConversionRate(conversionRate);
            }
            
            // 计算整体转化率
            if (firstUserCount == null) {
                firstUserCount = userCount;
            }
            if (firstUserCount > 0) {
                double overallConversionRate = (double) userCount / firstUserCount;
                result.setOverallConversionRate(overallConversionRate);
            }
            
            results.add(result);
            previousUserCount = userCount;
        }
        
        // 保存分析结果
        return funnelAnalysisResultRepository.saveAll(results);
    }

    @Override
    public List<FunnelAnalysisResult> getFunnelAnalysisResults(Long funnelId, Date startDate, Date endDate) {
        return funnelAnalysisResultRepository.findByFunnelIdAndAnalysisDateBetween(funnelId, startDate, endDate);
    }

    @Override
    public List<FunnelAnalysisResult> getLatestFunnelAnalysisResults(Long funnelId) {
        FunnelAnalysisResult latestResult = funnelAnalysisResultRepository.findLatestByFunnelId(funnelId);
        if (latestResult == null) {
            return new ArrayList<>();
        }
        return funnelAnalysisResultRepository.findByFunnelIdAndAnalysisDate(funnelId, latestResult.getAnalysisDate());
    }

    @Override
    public List<Long> findChurnedUsersInFunnel(Long funnelId, Integer stepIndex, Date analysisDate) {
        Funnel funnel = getFunnelById(funnelId);
        String[] steps = funnel.getFunnelSteps().split(",");
        
        if (stepIndex < 0 || stepIndex >= steps.length - 1) {
            throw new IllegalArgumentException("Invalid step index");
        }
        
        String currentStep = steps[stepIndex].trim();
        String nextStep = steps[stepIndex + 1].trim();
        
        // 这里应该根据实际的业务逻辑查询在当前步骤但未进入下一步的用户
        // 暂时返回空列表，实际项目中需要实现具体的查询逻辑
        return new ArrayList<>();
    }

    /**
     * 模拟每个步骤的用户数量
     * 实际项目中应该根据业务逻辑查询数据库或Elasticsearch
     */
    private Integer simulateUserCountForStep(Long funnelId, String stepName, Date analysisDate) {
        // 模拟数据，实际项目中需要替换为真实的查询逻辑
        switch (stepName) {
            case "首页":
                return 10000;
            case "详情页":
                return 3500;
            case "加购":
                return 1200;
            case "下单":
                return 450;
            default:
                return 0;
        }
    }
}
