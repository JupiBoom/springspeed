package com.yasinyuan.testspring.settlement.controller;

import com.yasinyuan.testspring.settlement.model.UserAccount;
import com.yasinyuan.testspring.settlement.model.SettlementAccount;
import com.yasinyuan.testspring.settlement.model.SettlementRecord;
import com.yasinyuan.testspring.settlement.service.AccountService;
import com.yasinyuan.testspring.settlement.service.SettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 结算控制器
 * 提供账户管理和结算相关的API接口
 */
@RestController
@RequestMapping("/api/settlement")
public class SettlementController {
    
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private SettlementService settlementService;
    
    // ==================== 用户账户管理 ====================
    
    @PostMapping("/user-account")
    public UserAccount createUserAccount(@RequestParam Long userId, @RequestParam String accountType) {
        return accountService.createUserAccount(userId, accountType);
    }
    
    @GetMapping("/user-account")
    public UserAccount getUserAccount(@RequestParam Long userId, @RequestParam String accountType) {
        return accountService.getUserAccount(userId, accountType);
    }
    
    @PutMapping("/user-account/increase")
    public UserAccount increaseUserAccountBalance(@RequestParam Long userId, 
                                                  @RequestParam String accountType, 
                                                  @RequestParam BigDecimal amount) {
        return accountService.increaseUserAccountBalance(userId, accountType, amount);
    }
    
    @PutMapping("/user-account/decrease")
    public UserAccount decreaseUserAccountBalance(@RequestParam Long userId, 
                                                  @RequestParam String accountType, 
                                                  @RequestParam BigDecimal amount) {
        return accountService.decreaseUserAccountBalance(userId, accountType, amount);
    }
    
    @PutMapping("/user-account/freeze")
    public UserAccount freezeUserAccountAmount(@RequestParam Long userId, 
                                               @RequestParam String accountType, 
                                               @RequestParam BigDecimal amount) {
        return accountService.freezeUserAccountAmount(userId, accountType, amount);
    }
    
    @PutMapping("/user-account/unfreeze")
    public UserAccount unfreezeUserAccountAmount(@RequestParam Long userId, 
                                                 @RequestParam String accountType, 
                                                 @RequestParam BigDecimal amount) {
        return accountService.unfreezeUserAccountAmount(userId, accountType, amount);
    }
    
    // ==================== 结算账户管理 ====================
    
    @PostMapping("/settlement-account")
    public SettlementAccount createSettlementAccount(@RequestParam Long ownerId, 
                                                     @RequestParam String accountType, 
                                                     @RequestParam String accountName, 
                                                     @RequestParam String bankName, 
                                                     @RequestParam String bankAccount, 
                                                     @RequestParam String accountHolder, 
                                                     @RequestParam String bankCode, 
                                                     @RequestParam String settlementCycle, 
                                                     @RequestParam BigDecimal settlementThreshold) {
        return accountService.createSettlementAccount(ownerId, accountType, accountName, 
                bankName, bankAccount, accountHolder, bankCode, settlementCycle, settlementThreshold);
    }
    
    @GetMapping("/settlement-account")
    public SettlementAccount getSettlementAccount(@RequestParam Long ownerId, 
                                                  @RequestParam String accountType) {
        return accountService.getSettlementAccount(ownerId, accountType);
    }
    
    @PutMapping("/settlement-account/increase-pending")
    public SettlementAccount increaseSettlementAccountPendingAmount(@RequestParam Long accountId, 
                                                                   @RequestParam BigDecimal amount) {
        return accountService.increaseSettlementAccountPendingAmount(accountId, amount);
    }
    
    // ==================== 结算记录管理 ====================
    
    @PostMapping("/settlement-record")
    public SettlementRecord createSettlementRecord(@RequestParam Long ownerId, 
                                                   @RequestParam Long accountId, 
                                                   @RequestParam String settlementType, 
                                                   @RequestParam String settlementPeriod, 
                                                   @RequestParam BigDecimal totalAmount, 
                                                   @RequestParam BigDecimal feeAmount, 
                                                   @RequestParam BigDecimal actualAmount) {
        return settlementService.createSettlementRecord(ownerId, accountId, settlementType, 
                settlementPeriod, totalAmount, feeAmount, actualAmount);
    }
    
    @GetMapping("/settlement-record/{settlementNo}")
    public SettlementRecord getSettlementRecordByNo(@PathVariable String settlementNo) {
        return settlementService.getSettlementRecordByNo(settlementNo);
    }
    
    @GetMapping("/settlement-records/owner")
    public List<SettlementRecord> getSettlementRecordsByOwnerId(@RequestParam Long ownerId, 
                                                                @RequestParam(required = false) String status, 
                                                                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate, 
                                                                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return settlementService.getSettlementRecordsByOwnerId(ownerId, status, startDate, endDate);
    }
    
    @PutMapping("/settlement-record/status")
    public SettlementRecord updateSettlementStatus(@RequestParam String settlementNo, 
                                                   @RequestParam String status, 
                                                   @RequestParam(required = false) String bankFlowNo, 
                                                   @RequestParam(required = false) String failReason) {
        return settlementService.updateSettlementStatus(settlementNo, status, bankFlowNo, failReason);
    }
    
    @PostMapping("/settlement-record/initiate")
    public SettlementRecord initiateSettlement(@RequestParam String settlementNo) {
        return settlementService.initiateSettlement(settlementNo);
    }
    
    @PostMapping("/settlement-record/batch-initiate")
    public List<SettlementRecord> batchInitiateSettlement(@RequestBody List<String> settlementNos) {
        return settlementService.batchInitiateSettlement(settlementNos);
    }
    
    @PostMapping("/auto-settlement")
    public List<SettlementRecord> autoSettlement() {
        return settlementService.autoSettlement();
    }
    
    @GetMapping("/settlement-records/pending")
    public List<SettlementRecord> getPendingSettlements() {
        return settlementService.getPendingSettlements();
    }
}