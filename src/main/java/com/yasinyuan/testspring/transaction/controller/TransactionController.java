package com.yasinyuan.testspring.transaction.controller;

import com.yasinyuan.testspring.transaction.model.TransactionRecord;
import com.yasinyuan.testspring.transaction.service.TransactionRecordService;
import com.yasinyuan.testspring.tools.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 交易记录控制器
 * @author yinyuan
 */
@RestController
@RequestMapping("/api/transaction")
@Api(tags = "交易管理")
public class TransactionController {
    
    @Autowired
    private TransactionRecordService transactionRecordService;

    @ApiOperation("创建交易记录")
    @PostMapping("/create")
    public R create(@RequestBody TransactionRecord record) {
        TransactionRecord result = transactionRecordService.createTransaction(record);
        return R.ok(result);
    }

    @ApiOperation("根据交易流水号查询")
    @GetMapping("/getByNo")
    public R getByTransactionNo(@RequestParam String transactionNo) {
        TransactionRecord record = transactionRecordService.getByTransactionNo(transactionNo);
        return R.ok(record);
    }

    @ApiOperation("根据订单号查询")
    @GetMapping("/getByOrderNo")
    public R getByOrderNo(@RequestParam String orderNo) {
        TransactionRecord record = transactionRecordService.getByOrderNo(orderNo);
        return R.ok(record);
    }

    @ApiOperation("更新支付状态")
    @PostMapping("/updateStatus")
    public R updatePaymentStatus(@RequestParam String transactionNo, @RequestParam String status) {
        boolean result = transactionRecordService.updatePaymentStatus(transactionNo, status);
        return result ? R.ok() : R.error("更新失败");
    }

    @ApiOperation("分页查询交易记录")
    @GetMapping("/list")
    public R list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) String orderNo,
            @RequestParam(required = false) String status) {
        
        Map<String, Object> params = new HashMap<>();
        if (orderNo != null) {
            params.put("orderNo", orderNo);
        }
        if (status != null) {
            params.put("status", status);
        }
        
        List<TransactionRecord> list = transactionRecordService.findByPage(params, page, limit);
        int total = transactionRecordService.count(params);
        
        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", total);
        result.put("page", page);
        result.put("limit", limit);
        
        return R.ok(result);
    }
}
