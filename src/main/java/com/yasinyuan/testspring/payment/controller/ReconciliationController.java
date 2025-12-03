package com.yasinyuan.testspring.payment.controller;

import com.yasinyuan.testspring.tools.R;
import com.yasinyuan.testspring.payment.enums.PaymentChannel;
import com.yasinyuan.testspring.payment.model.ReconciliationRecord;
import com.yasinyuan.testspring.payment.service.ReconciliationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 对账控制器
 */
@RestController
@RequestMapping("/reconciliation")
public class ReconciliationController {
    
    @Autowired
    private ReconciliationService reconciliationService;
    
    /**
     * 下载渠道对账单
     * @param channel 支付渠道
     * @param date 对账日期
     * @return 对账单文件路径
     */
    @GetMapping("/download-channel-bill")
    public R downloadChannelBill(@RequestParam PaymentChannel channel,
                                @RequestParam Date date) {
        try {
            String filePath = reconciliationService.downloadChannelBill(channel, date);
            return R.ok().put("data", filePath);
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }
    
    /**
     * 生成系统对账单
     * @param channel 支付渠道
     * @param date 对账日期
     * @return 对账单文件路径
     */
    @GetMapping("/generate-system-bill")
    public R generateSystemBill(@RequestParam PaymentChannel channel,
                               @RequestParam Date date) {
        try {
            String filePath = reconciliationService.generateSystemBill(channel, date);
            return R.ok().put("data", filePath);
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }
    
    /**
     * 执行对账
     * @param channel 支付渠道
     * @param date 对账日期
     * @return 对账结果
     */
    @PostMapping("/execute")
    public R executeReconciliation(@RequestParam PaymentChannel channel,
                                  @RequestParam Date date) {
        try {
            ReconciliationRecord record = reconciliationService.executeReconciliation(channel, date);
            return R.ok().put("data", record);
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }
    
    /**
     * 批量执行对账
     * @param date 对账日期
     * @return 对账结果列表
     */
    @PostMapping("/batch-execute")
    public R batchExecuteReconciliation(@RequestParam Date date) {
        try {
            List<ReconciliationRecord> records = reconciliationService.batchExecuteReconciliation(date);
            return R.ok().put("data", records);
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }
    
    /**
     * 查询对账记录
     * @param channel 支付渠道
     * @param date 对账日期
     * @param status 对账状态
     * @param page 页码
     * @param size 每页数量
     * @return 对账记录列表
     */
    @GetMapping("/list")
    public R queryReconciliations(@RequestParam(required = false) PaymentChannel channel,
                                 @RequestParam(required = false) Date date,
                                 @RequestParam(required = false) Integer status,
                                 @RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "10") int size) {
        try {
            List<ReconciliationRecord> records = reconciliationService.queryReconciliations(channel, date, status, page, size);
            return R.ok().put("data", records);
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }
    
    /**
     * 获取对账差异详情
     * @param reconciliationId 对账记录ID
     * @return 差异详情列表
     */
    @GetMapping("/diff/{reconciliationId}")
    public R getReconciliationDiff(@PathVariable Long reconciliationId) {
        try {
            List<String> diffDetails = reconciliationService.getReconciliationDiff(reconciliationId);
            return R.ok().put("data", diffDetails);
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }
}
