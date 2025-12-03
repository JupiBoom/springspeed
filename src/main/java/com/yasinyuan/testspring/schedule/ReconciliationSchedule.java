package com.yasinyuan.testspring.schedule;

import com.yasinyuan.testspring.reconciliation.ReconciliationService;
import com.yasinyuan.testspring.reconciliation.ReconciliationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

/**
 * 对账定时任务
 */
@Component
public class ReconciliationSchedule {

    private static final Logger logger = LoggerFactory.getLogger(ReconciliationSchedule.class);

    @Autowired
    private ReconciliationService reconciliationService;

    /**
     * 每天凌晨2点下载前一天的渠道对账单并进行自动对账
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void autoReconcileDaily() {
        logger.info("开始执行每日自动对账任务");
        
        // 获取前一天的日期
        Date billDate = getYesterday();
        
        // 支持的支付渠道列表
        String[] payChannels = {"WECHAT_PAY", "ALIPAY", "UNION_PAY"};
        
        for (String payChannel : payChannels) {
            try {
                logger.info("开始对账: 渠道={}, 日期={}", payChannel, billDate);
                
                // 执行自动对账
                ReconciliationResult result = reconciliationService.autoReconcile(payChannel, billDate);
                
                // 生成对账报告
                String report = reconciliationService.generateReconciliationReport(payChannel, billDate);
                
                // 实际应该将对账报告保存到文件系统或发送邮件
                logger.info("对账完成: 渠道={}, 日期={}, 结果={}", payChannel, billDate, result.isSuccess());
                logger.info("对账报告: \n{}", report);
                
            } catch (Exception e) {
                logger.error("对账失败: 渠道={}, 日期={}", payChannel, billDate, e);
            }
        }
        
        logger.info("每日自动对账任务执行完成");
    }

    /**
     * 每分钟检查一次是否有需要手动处理的对账差异
     */
    @Scheduled(cron = "0 * * * * ?")
    public void checkReconciliationDifferences() {
        logger.info("开始检查对账差异");
        
        // 实际应该从数据库中查询状态为CHANNEL_UNILATERAL、SYSTEM_UNILATERAL或AMOUNT_MISMATCH的对账记录
        // 然后发送告警通知给相关人员
        
        logger.info("对账差异检查完成");
    }

    /**
     * 获取前一天的日期
     * @return 前一天的日期
     */
    private Date getYesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}
