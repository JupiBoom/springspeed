package com.yasinyuan.testspring.payment.controller;

import com.yasinyuan.testspring.tools.R;
import com.yasinyuan.testspring.payment.model.MerchantAccount;
import com.yasinyuan.testspring.payment.service.MerchantAccountService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 商户账户控制器
 */
@RestController
@RequestMapping("/merchant/account")
public class MerchantAccountController {
    
    @Autowired
    private MerchantAccountService merchantAccountService;
    
    /**
     * 获取商户账户信息
     * @param merchantId 商户ID
     * @return 商户账户信息
     */
    @GetMapping("/{merchantId}")
    public R getMerchantAccount(@PathVariable Long merchantId) {
        try {
            MerchantAccount account = merchantAccountService.getByMerchantId(merchantId.toString());
            return R.ok().put("data", account);
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }
    
    /**
     * 创建商户账户
     * @param merchantAccount 商户账户信息
     * @return 创建结果
     */
    @PostMapping("/create")
    public R createMerchantAccount(@RequestBody MerchantAccount merchantAccount) {
        try {
            merchantAccountService.createMerchantAccount(merchantAccount);
            return R.ok();
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }
    
    /**
     * 更新商户账户
     * @param merchantAccount 商户账户信息
     * @return 更新结果
     */
    @PostMapping("/update")
    public R updateMerchantAccount(@RequestBody MerchantAccount merchantAccount) {
        try {
            merchantAccountService.updateMerchantAccount(merchantAccount);
            return R.ok();
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }
    
    /**
     * 冻结商户账户金额
     * @param merchantId 商户ID
     * @param amount 冻结金额
     * @return 冻结结果
     */
    @PostMapping("/freeze")
    public R freezeAmount(@RequestParam Long merchantId, @RequestParam String amount) {
        try {
            merchantAccountService.freezeAmount(merchantId.toString(), new BigDecimal(amount));
            return R.ok();
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }
    
    /**
     * 解冻商户账户金额
     * @param merchantId 商户ID
     * @param amount 解冻金额
     * @return 解冻结果
     */
    @PostMapping("/unfreeze")
    public R unfreezeAmount(@RequestParam Long merchantId, @RequestParam String amount) {
        try {
            merchantAccountService.unfreezeAmount(merchantId.toString(), new BigDecimal(amount));
            return R.ok();
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }
    
    /**
     * 增加商户账户余额
     * @param merchantId 商户ID
     * @param amount 增加金额
     * @return 增加结果
     */
    @PostMapping("/add-balance")
    public R addBalance(@RequestParam Long merchantId, @RequestParam String amount) {
        try {
            merchantAccountService.addBalance(merchantId.toString(), new BigDecimal(amount));
            return R.ok();
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }
    
    /**
     * 减少商户账户余额
     * @param merchantId 商户ID
     * @param amount 减少金额
     * @return 减少结果
     */
    @PostMapping("/deduct-balance")
    public R deductBalance(@RequestParam Long merchantId, @RequestParam String amount) {
        try {
            merchantAccountService.subtractBalance(merchantId.toString(), new BigDecimal(amount));
            return R.ok();
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }
}
