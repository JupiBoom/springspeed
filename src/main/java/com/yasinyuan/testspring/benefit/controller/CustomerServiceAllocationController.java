package com.yasinyuan.testspring.benefit.controller;

import com.yasinyuan.testspring.benefit.entity.CustomerServiceAllocation;
import com.yasinyuan.testspring.benefit.service.CustomerServiceAllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 客服分配Controller
 */
@RestController
@RequestMapping("/customer-service-allocation")
public class CustomerServiceAllocationController {

    @Autowired
    private CustomerServiceAllocationService customerServiceAllocationService;

    /**
     * 查询所有客服分配记录
     */
    @GetMapping("/list")
    public List<CustomerServiceAllocation> list() {
        return customerServiceAllocationService.list();
    }

    /**
     * 根据ID查询客服分配记录
     */
    @GetMapping("/{id}")
    public CustomerServiceAllocation getById(@PathVariable Long id) {
        return customerServiceAllocationService.getById(id);
    }

    /**
     * 根据会员ID查询客服分配记录
     */
    @GetMapping("/member/{memberId}")
    public CustomerServiceAllocation getByMemberId(@PathVariable Long memberId) {
        return customerServiceAllocationService.lambdaQuery()
                .eq(CustomerServiceAllocation::getMemberId, memberId)
                .one();
    }

    /**
     * 新增客服分配记录
     */
    @PostMapping
    public boolean save(@RequestBody CustomerServiceAllocation customerServiceAllocation) {
        return customerServiceAllocationService.save(customerServiceAllocation);
    }

    /**
     * 修改客服分配记录
     */
    @PutMapping
    public boolean update(@RequestBody CustomerServiceAllocation customerServiceAllocation) {
        return customerServiceAllocationService.updateById(customerServiceAllocation);
    }

    /**
     * 删除客服分配记录
     */
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return customerServiceAllocationService.removeById(id);
    }
}
