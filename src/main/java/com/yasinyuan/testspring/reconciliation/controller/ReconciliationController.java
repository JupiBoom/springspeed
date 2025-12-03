package com.yasinyuan.testspring.reconciliation.controller;

import com.yasinyuan.testspring.reconciliation.model.ReconciliationRecord;
import com.yasinyuan.testspring.reconciliation.model.ReconciliationDiscrepancy;
import com.yasinyuan.testspring.reconciliation.service.ReconciliationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 对账控制器
 * 提供对账相关的API接口
 */
@RestController
@RequestMapping("/api/reconciliation")
public class ReconciliationController {
    
    @Autowired
    private ReconciliationService reconciliationService;
    
    /**
     * 创建对账任务
     * @param reconciliationDate 对账日期
     * @param reconciliationType 对账类型
     * @param paymentChannel 支付渠道（可选，默认ALL）
     * @return 对账记录
     */
    @PostMapping("/task")
    public ReconciliationRecord createReconciliationTask(@RequestParam String reconciliationDate,
                                                         @RequestParam String reconciliationType,
                                                         @RequestParam(required = false, defaultValue = "ALL") String paymentChannel) {
        return reconciliationService.createReconciliationTask(reconciliationDate, reconciliationType, paymentChannel);
    }
    
    /**
     * 根据对账流水号查询对账记录
     * @param reconciliationNo 对账流水号
     * @return 对账记录
     */
    @GetMapping("/record/{reconciliationNo}")
    public ReconciliationRecord getReconciliationRecordByNo(@PathVariable String reconciliationNo) {
        return reconciliationService.getReconciliationRecordByNo(reconciliationNo);
    }
    
    /**
     * 根据日期和类型查询对账记录
     * @param reconciliationDate 对账日期
     * @param reconciliationType 对账类型
     * @param paymentChannel 支付渠道（可选）
     * @return 对账记录列表
     */
    @GetMapping("/records")
    public List<ReconciliationRecord> getReconciliationRecordsByDateAndType(@RequestParam String reconciliationDate,
                                                                            @RequestParam String reconciliationType,
                                                                            @RequestParam(required = false) String paymentChannel) {
        return reconciliationService.getReconciliationRecordsByDateAndType(reconciliationDate, reconciliationType, paymentChannel);
    }
    
    /**
     * 发起对账
     * @param reconciliationNo 对账流水号
     * @return 对账记录
     */
    @PostMapping("/initiate/{reconciliationNo}")
    public ReconciliationRecord initiateReconciliation(@PathVariable String reconciliationNo) {
        return reconciliationService.initiateReconciliation(reconciliationNo);
    }
    
    /**
     * 批量发起对账
     * @param reconciliationNos 对账流水号列表
     * @return 对账记录列表
     */
    @PostMapping("/batch/initiate")
    public List<ReconciliationRecord> batchInitiateReconciliation(@RequestBody List<String> reconciliationNos) {
        return reconciliationService.batchInitiateReconciliation(reconciliationNos);
    }
    
    /**
     * 自动对账
     * @param reconciliationDate 对账日期（可选，默认昨天）
     * @return 对账记录列表
     */
    @PostMapping("/auto")
    public List<ReconciliationRecord> autoReconciliation(@RequestParam(required = false) String reconciliationDate) {
        return reconciliationService.autoReconciliation(reconciliationDate);
    }
    
    /**
     * 查询待对账的任务
     * @return 待对账任务列表
     */
    @GetMapping("/pending")
    public List<ReconciliationRecord> getPendingReconciliations() {
        return reconciliationService.getPendingReconciliations();
    }
    
    /**
     * 根据对账流水号查询差异记录
     * @param reconciliationNo 对账流水号
     * @return 差异记录列表
     */
    @GetMapping("/discrepancies/{reconciliationNo}")
    public List<ReconciliationDiscrepancy> getDiscrepanciesByReconciliationNo(@PathVariable String reconciliationNo) {
        return reconciliationService.getDiscrepanciesByReconciliationNo(reconciliationNo);
    }
    
    /**
     * 根据处理状态查询差异记录
     * @param status 处理状态
     * @return 差异记录列表
     */
    @GetMapping("/discrepancies/status/{status}")
    public List<ReconciliationDiscrepancy> getDiscrepanciesByStatus(@PathVariable String status) {
        return reconciliationService.getDiscrepanciesByStatus(status);
    }
    
    /**
     * 处理差异记录
     * @param discrepancyId 差异记录ID
     * @param status 处理状态
     * @param handler 处理人
     * @param handleRemark 处理备注
     * @return 差异记录
     */
    @PostMapping("/discrepancy/handle/{discrepancyId}")
    public ReconciliationDiscrepancy handleDiscrepancy(@PathVariable Long discrepancyId,
                                                       @RequestParam String status,
                                                       @RequestParam String handler,
                                                       @RequestParam(required = false) String handleRemark) {
        return reconciliationService.handleDiscrepancy(discrepancyId, status, handler, handleRemark);
    }
}