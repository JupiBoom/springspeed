package com.yasinyuan.testspring.service.impl;

import com.yasinyuan.testspring.dao.PointsAccountMapper;
import com.yasinyuan.testspring.dao.PointsRecordMapper;
import com.yasinyuan.testspring.model.PointsAccount;
import com.yasinyuan.testspring.model.PointsRecord;
import com.yasinyuan.testspring.service.PointsService;
import com.yasinyuan.testspring.core.AbstractService;
import com.yasinyuan.testspring.service.PointsGainRuleService;
import com.yasinyuan.testspring.tools.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 积分服务实现类
 * @author yinyuan
 * @since 2024-01-01
 */
@Service
public class PointsServiceImpl extends AbstractService<PointsAccount> implements PointsService {
    @Resource
    private PointsAccountMapper pointsAccountMapper;
    
    @Resource
    private PointsRecordMapper pointsRecordMapper;
    
    @Resource
    private PointsGainRuleService pointsGainRuleService;

    @Override
    public PointsAccount findByMemberId(Long memberId) {
        return pointsAccountMapper.findByMemberId(memberId);
    }

    @Override
    @Transactional
    public void addPoints(Long memberId, Integer points, Integer businessType, Long businessId, String remark) {
        // 获取积分账户
        PointsAccount account = getOrCreatePointsAccount(memberId);
        
        // 检查积分获取规则
        if (businessType != null) {
            PointsGainRule rule = pointsGainRuleService.findEnabledRuleByBusinessType(businessType);
            if (rule != null) {
                // 检查每日上限
                if (!pointsGainRuleService.checkDailyLimit(memberId, businessType, points)) {
                    throw new RuntimeException("今日积分获取已达上限");
                }
                
                // 检查每月上限
                if (!pointsGainRuleService.checkMonthlyLimit(memberId, businessType, points)) {
                    throw new RuntimeException("本月积分获取已达上限");
                }
            }
        }
        
        Integer beforeAvailable = account.getAvailablePoints();
        Integer afterAvailable = beforeAvailable + points;
        Integer totalPoints = account.getTotalPoints() + points;
        
        // 更新积分账户
        account.setAvailablePoints(afterAvailable);
        account.setTotalPoints(totalPoints);
        account.setUpdateTime(new Date());
        pointsAccountMapper.updateByPrimaryKeySelective(account);
        
        // 记录积分变化
        PointsRecord record = new PointsRecord();
        record.setMemberId(memberId);
        record.setAccountId(account.getId());
        record.setChangeType(1); // 增加
        record.setChangeValue(points);
        record.setBeforeAvailable(beforeAvailable);
        record.setAfterAvailable(afterAvailable);
        record.setBeforeFrozen(account.getFrozenPoints());
        record.setAfterFrozen(account.getFrozenPoints());
        record.setBusinessType(businessType);
        record.setBusinessId(businessId);
        
        // 设置积分过期时间
        if (businessType != null) {
            PointsGainRule rule = pointsGainRuleService.findEnabledRuleByBusinessType(businessType);
            if (rule != null && rule.getValidityDays() != null) {
                record.setExpireTime(DateUtils.addDays(new Date(), rule.getValidityDays()));
            }
        }
        
        record.setRemark(remark);
        record.setCreateTime(new Date());
        pointsRecordMapper.insertSelective(record);
        
        // TODO: 发送积分变动通知
    }

    @Override
    @Transactional
    public void deductPoints(Long memberId, Integer points, Integer businessType, Long businessId, String remark) {
        // 获取积分账户
        PointsAccount account = getOrCreatePointsAccount(memberId);
        
        // 检查可用积分是否足够
        if (account.getAvailablePoints() < points) {
            throw new RuntimeException("可用积分不足");
        }
        
        Integer beforeAvailable = account.getAvailablePoints();
        Integer afterAvailable = beforeAvailable - points;
        
        // 更新积分账户
        account.setAvailablePoints(afterAvailable);
        account.setUpdateTime(new Date());
        pointsAccountMapper.updateByPrimaryKeySelective(account);
        
        // 记录积分变化
        PointsRecord record = new PointsRecord();
        record.setMemberId(memberId);
        record.setAccountId(account.getId());
        record.setChangeType(2); // 减少
        record.setChangeValue(points);
        record.setBeforeAvailable(beforeAvailable);
        record.setAfterAvailable(afterAvailable);
        record.setBeforeFrozen(account.getFrozenPoints());
        record.setAfterFrozen(account.getFrozenPoints());
        record.setBusinessType(businessType);
        record.setBusinessId(businessId);
        record.setRemark(remark);
        record.setCreateTime(new Date());
        pointsRecordMapper.insertSelective(record);
        
        // TODO: 发送积分变动通知
    }

    @Override
    @Transactional
    public void freezePoints(Long memberId, Integer points, Integer businessType, Long businessId, String remark) {
        // 获取积分账户
        PointsAccount account = getOrCreatePointsAccount(memberId);
        
        // 检查可用积分是否足够
        if (account.getAvailablePoints() < points) {
            throw new RuntimeException("可用积分不足");
        }
        
        Integer beforeAvailable = account.getAvailablePoints();
        Integer afterAvailable = beforeAvailable - points;
        Integer beforeFrozen = account.getFrozenPoints();
        Integer afterFrozen = beforeFrozen + points;
        
        // 更新积分账户
        account.setAvailablePoints(afterAvailable);
        account.setFrozenPoints(afterFrozen);
        account.setUpdateTime(new Date());
        pointsAccountMapper.updateByPrimaryKeySelective(account);
        
        // 记录积分变化
        PointsRecord record = new PointsRecord();
        record.setMemberId(memberId);
        record.setAccountId(account.getId());
        record.setChangeType(3); // 冻结
        record.setChangeValue(points);
        record.setBeforeAvailable(beforeAvailable);
        record.setAfterAvailable(afterAvailable);
        record.setBeforeFrozen(beforeFrozen);
        record.setAfterFrozen(afterFrozen);
        record.setBusinessType(businessType);
        record.setBusinessId(businessId);
        record.setRemark(remark);
        record.setCreateTime(new Date());
        pointsRecordMapper.insertSelective(record);
    }

    @Override
    @Transactional
    public void unfreezePoints(Long memberId, Integer points, Integer businessType, Long businessId, String remark) {
        // 获取积分账户
        PointsAccount account = getOrCreatePointsAccount(memberId);
        
        // 检查冻结积分是否足够
        if (account.getFrozenPoints() < points) {
            throw new RuntimeException("冻结积分不足");
        }
        
        Integer beforeAvailable = account.getAvailablePoints();
        Integer afterAvailable = beforeAvailable + points;
        Integer beforeFrozen = account.getFrozenPoints();
        Integer afterFrozen = beforeFrozen - points;
        
        // 更新积分账户
        account.setAvailablePoints(afterAvailable);
        account.setFrozenPoints(afterFrozen);
        account.setUpdateTime(new Date());
        pointsAccountMapper.updateByPrimaryKeySelective(account);
        
        // 记录积分变化
        PointsRecord record = new PointsRecord();
        record.setMemberId(memberId);
        record.setAccountId(account.getId());
        record.setChangeType(4); // 解冻
        record.setChangeValue(points);
        record.setBeforeAvailable(beforeAvailable);
        record.setAfterAvailable(afterAvailable);
        record.setBeforeFrozen(beforeFrozen);
        record.setAfterFrozen(afterFrozen);
        record.setBusinessType(businessType);
        record.setBusinessId(businessId);
        record.setRemark(remark);
        record.setCreateTime(new Date());
        pointsRecordMapper.insertSelective(record);
    }

    @Override
    @Transactional
    public void processPointsExpiration() {
        // 查询今天过期的积分记录
        Date today = new Date();
        List<PointsRecord> expiredRecords = pointsRecordMapper.findExpiredRecords(today);
        
        // 处理过期积分
        for (PointsRecord record : expiredRecords) {
            // 只处理未过期的增加积分记录
            if (record.getChangeType() == 1 && record.getExpireTime() != null && record.getExpireTime().before(today)) {
                PointsAccount account = pointsAccountMapper.selectByPrimaryKey(record.getAccountId());
                if (account != null) {
                    Integer beforeAvailable = account.getAvailablePoints();
                    Integer afterAvailable = beforeAvailable - record.getChangeValue();
                    
                    // 更新积分账户
                    account.setAvailablePoints(afterAvailable);
                    account.setUpdateTime(new Date());
                    pointsAccountMapper.updateByPrimaryKeySelective(account);
                    
                    // 记录积分过期
                    PointsRecord expireRecord = new PointsRecord();
                    expireRecord.setMemberId(record.getMemberId());
                    expireRecord.setAccountId(record.getAccountId());
                    expireRecord.setChangeType(5); // 过期
                    expireRecord.setChangeValue(record.getChangeValue());
                    expireRecord.setBeforeAvailable(beforeAvailable);
                    expireRecord.setAfterAvailable(afterAvailable);
                    expireRecord.setBeforeFrozen(account.getFrozenPoints());
                    expireRecord.setAfterFrozen(account.getFrozenPoints());
                    expireRecord.setBusinessType(7); // 过期扣除
                    expireRecord.setBusinessId(record.getId());
                    expireRecord.setRemark("积分过期扣除");
                    expireRecord.setCreateTime(new Date());
                    pointsRecordMapper.insertSelective(expireRecord);
                    
                    // TODO: 发送积分过期通知
                }
            }
        }
    }

    @Override
    public List<PointsRecord> getPointsRecordsByMemberId(Long memberId) {
        return pointsRecordMapper.findByMemberId(memberId);
    }

    @Override
    public PointsStatistics getPointsStatistics() {
        PointsStatistics statistics = new PointsStatistics();
        
        // 总发放积分
        // TODO: 实现总发放积分查询
        statistics.setTotalPointsIssued(0);
        
        // 总使用积分
        // TODO: 实现总使用积分查询
        statistics.setTotalPointsUsed(0);
        
        // 总过期积分
        // TODO: 实现总过期积分查询
        statistics.setTotalPointsExpired(0);
        
        // 积分使用率
        if (statistics.getTotalPointsIssued() > 0) {
            statistics.setPointsUtilizationRate((double) statistics.getTotalPointsUsed() / statistics.getTotalPointsIssued());
        } else {
            statistics.setPointsUtilizationRate(0.0);
        }
        
        return statistics;
    }
    
    /**
     * 获取或创建积分账户
     */
    private PointsAccount getOrCreatePointsAccount(Long memberId) {
        PointsAccount account = pointsAccountMapper.findByMemberId(memberId);
        if (account == null) {
            account = new PointsAccount();
            account.setMemberId(memberId);
            account.setAvailablePoints(0);
            account.setFrozenPoints(0);
            account.setTotalPoints(0);
            account.setCreateTime(new Date());
            account.setUpdateTime(new Date());
            pointsAccountMapper.insertSelective(account);
        }
        return account;
    }
}