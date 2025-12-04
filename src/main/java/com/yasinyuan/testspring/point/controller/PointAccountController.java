package com.yasinyuan.testspring.point.controller;

import com.yasinyuan.testspring.point.entity.PointAccount;
import com.yasinyuan.testspring.point.service.PointAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 积分账户Controller
 */
@RestController
@RequestMapping("/point-account")
public class PointAccountController {

    @Autowired
    private PointAccountService pointAccountService;

    /**
     * 根据会员ID查询积分账户
     */
    @GetMapping("/member/{memberId}")
    public PointAccount getByMemberId(@PathVariable Long memberId) {
        return pointAccountService.lambdaQuery()
                .eq(PointAccount::getMemberId, memberId)
                .one();
    }

    /**
     * 新增积分账户
     */
    @PostMapping
    public boolean save(@RequestBody PointAccount pointAccount) {
        return pointAccountService.save(pointAccount);
    }

    /**
     * 修改积分账户
     */
    @PutMapping
    public boolean update(@RequestBody PointAccount pointAccount) {
        return pointAccountService.updateById(pointAccount);
    }
}
