package com.yasinyuan.testspring.transaction.service.impl;

import com.github.pagehelper.PageHelper;
import com.yasinyuan.testspring.transaction.dao.TransactionRecordMapper;
import com.yasinyuan.testspring.transaction.model.TransactionRecord;
import com.yasinyuan.testspring.transaction.service.TransactionRecordService;
import com.yasinyuan.testspring.transaction.util.TransactionNoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 交易记录服务实现
 * @author yinyuan
 */
@Service
public class TransactionRecordServiceImpl implements TransactionRecordService {
    
    @Autowired
    private TransactionRecordMapper transactionRecordMapper;
    
    @Autowired
    private TransactionNoGenerator transactionNoGenerator;

    @Override
    @Transactional
    public TransactionRecord createTransaction(TransactionRecord record) {
        record.setTransactionNo(transactionNoGenerator.generate());
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());
        transactionRecordMapper.insertSelective(record);
        return record;
    }

    @Override
    public TransactionRecord getByTransactionNo(String transactionNo) {
        return transactionRecordMapper.selectByPrimaryKey(transactionNo);
    }

    @Override
    public TransactionRecord getByOrderNo(String orderNo) {
        return transactionRecordMapper.selectByOrderNo(orderNo);
    }

    @Override
    public TransactionRecord getByPaymentNo(String paymentNo) {
        return transactionRecordMapper.selectByPaymentNo(paymentNo);
    }

    @Override
    @Transactional
    public boolean updatePaymentStatus(String transactionNo, String status) {
        return transactionRecordMapper.updatePaymentStatus(transactionNo, status) > 0;
    }

    @Override
    public List<TransactionRecord> findByPage(Map<String, Object> params, int page, int limit) {
        PageHelper.startPage(page, limit);
        // TODO: 根据参数条件查询
        return transactionRecordMapper.selectAll();
    }

    @Override
    public List<TransactionRecord> findTimeoutTransactions() {
        return transactionRecordMapper.selectTimeoutTransactions(new Date());
    }

    @Override
    public int count(Map<String, Object> params) {
        // TODO: 根据参数条件统计
        return (int) transactionRecordMapper.selectCount(null);
    }
}
