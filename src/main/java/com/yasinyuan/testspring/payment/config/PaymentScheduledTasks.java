package com.yasinyuan.testspring.payment.config;

import com.yasinyuan.testspring.payment.service.PaymentService;
import com.yasinyuan.testspring.payment.service.ReconciliationService;
import com.yasinyuan.testspring.payment.service.SettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

/**
 * 支付系统定时任务配置
 */
@Component
public class PaymentScheduledTasks {
    
    @Autowired
    private PaymentService paymentService;
    
    @Autowired
    private ReconciliationService reconciliationService;
    
    @Autowired
    private SettlementService settlementService;
    
    /**
     * 处理超时订单
     * 每5分钟执行一次
     */
    @Scheduled(cron = "0 */5 * * * ?")
    public void processTimeoutOrders() {
        try {
            int count = paymentService.processTimeoutOrders();
            System.out.println("处理超时订单完成，共处理了 " + count + " 个订单");
        } catch (Exception e) {
            System.err.println("处理超时订单失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 自动对账
     * 每天凌晨2点执行一次，对前一天的交易进行对账
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void autoReconciliation() {
        try {
            // 获取前一天的日期
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -1);
            Date yesterday = calendar.getTime();
            
            reconciliationService.batchExecuteReconciliation(yesterday);
            System.out.println("自动对账完成，对账日期: " + yesterday);
        } catch (Exception e) {
            System.err.println("自动对账失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 自动生成结算单
     * 每天凌晨3点执行一次，生成前一天的结算单
     */
    @Scheduled(cron = "0 0 3 * * ?")
    public void autoGenerateSettlements() {
        try {
            // 获取前一天的日期
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -1);
            Date yesterday = calendar.getTime();
            
            int count = settlementService.batchCreateSettlements(yesterday);
            System.out.println("自动生成结算单完成，生成了 " + count + " 个结算单，结算日期: " + yesterday);
        } catch (Exception e) {
            System.err.println("自动生成结算单失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 自动执行结算
     * 每天凌晨4点执行一次，执行前一天生成的结算单
     */
    @Scheduled(cron = "0 0 4 * * ?")
    public void autoExecuteSettlements() {
        try {
            // 获取前一天的日期
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -1);
            Date yesterday = calendar.getTime();
            
            int count = settlementService.batchExecuteSettlements(yesterday);
            System.out.println("自动执行结算完成，执行了 " + count + " 个结算单，结算日期: " + yesterday);
        } catch (Exception e) {
            System.err.println("自动执行结算失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
