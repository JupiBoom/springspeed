package com.yasinyuan.testspring.payment.controller;

import com.yasinyuan.testspring.tools.R;
import java.math.BigDecimal;
import com.yasinyuan.testspring.payment.model.SettlementRecord;
import com.yasinyuan.testspring.payment.service.SettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 结算控制器
 */
@RestController
@RequestMapping("/settlement")
public class SettlementController {
    
    @Autowired
    private SettlementService settlementService;
    
    /**
     * 创建结算单
     * @param merchantId 商户ID
     * @param settlementDate 结算日期
     * @return 结算单信息
     */
    @PostMapping("/create")
    public R createSettlement(@RequestParam String merchantId,
                             @RequestParam Date cycleStartTime,
                             @RequestParam Date cycleEndTime) {
        try {
            SettlementRecord settlement = settlementService.createSettlement(merchantId, cycleStartTime, cycleEndTime);
            return R.ok().put("data", settlement);
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }
    
    /**
     * 执行结算
     * @param settlementNo 结算单号
     * @return 结算结果
     */
    @PostMapping("/execute/{settlementNo}")
    public R executeSettlement(@PathVariable String settlementNo) {
        try {
            settlementService.executeSettlement(settlementNo);
            return R.ok();
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }
    
    /**
     * 批量生成结算单
     * @param settlementDate 结算日期
     * @return 生成结果
     */
    @PostMapping("/batch-create")
    public R batchCreateSettlements(@RequestParam Date settlementDate) {
        try {
            int count = settlementService.batchCreateSettlements(settlementDate);
            return R.ok().put("data", "生成了 " + count + " 个结算单");
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }
    
    /**
     * 批量执行结算
     * @param settlementDate 结算日期
     * @return 执行结果
     */
    @PostMapping("/batch-execute")
    public R batchExecuteSettlements() {
        try {
            int count = settlementService.batchExecuteSettlements();
            return R.ok().put("data", "执行了 " + count + " 个结算单");
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }
    
    /**
     * 根据商户ID查询结算记录
     * @param merchantId 商户ID
     * @param page 页码
     * @param size 每页数量
     * @return 结算记录列表
     */
    @GetMapping("/list/{merchantId}")
    public R querySettlementsByMerchant(@PathVariable String merchantId,
                                        @RequestParam(required = false) Integer status,
                                        @RequestParam(defaultValue = "1") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        try {
            List<SettlementRecord> settlements = settlementService.querySettlementsByMerchant(merchantId, status, page, size);
            return R.ok().put("data", settlements);
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }
    
    /**
     * 查询结算单详情
     * @param settlementNo 结算单号
     * @return 结算单详情
     */
    @GetMapping("/detail/{settlementNo}")
    public R getSettlementDetail(@PathVariable String settlementNo) {
        try {
            SettlementRecord settlement = settlementService.findById(settlementNo);
            return R.ok().put("data", settlement);
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }
}
