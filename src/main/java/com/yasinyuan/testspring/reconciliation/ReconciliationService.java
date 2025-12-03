package com.yasinyuan.testspring.reconciliation;

import java.util.Date;
import java.util.List;

/**
 * 对账服务接口
 */
public interface ReconciliationService {

    /**
     * 下载渠道对账单
     * @param payChannel 支付渠道
     * @param billDate 账单日期
     * @return 对账单内容
     */
    String downloadChannelBill(String payChannel, Date billDate);

    /**
     * 解析渠道对账单
     * @param payChannel 支付渠道
     * @param billContent 对账单内容
     * @return 解析后的渠道交易记录列表
     */
    List<ChannelTransactionRecord> parseChannelBill(String payChannel, String billContent);

    /**
     * 获取系统交易记录
     * @param payChannel 支付渠道
     * @param billDate 账单日期
     * @return 系统交易记录列表
     */
    List<SystemTransactionRecord> getSystemTransactionRecords(String payChannel, Date billDate);

    /**
     * 自动对账
     * @param payChannel 支付渠道
     * @param billDate 账单日期
     * @return 对账结果
     */
    ReconciliationResult autoReconcile(String payChannel, Date billDate);

    /**
     * 手动对账
     * @param reconciliationRecord 对账记录
     * @return 对账是否成功
     */
    boolean manualReconcile(ReconciliationRecord reconciliationRecord);

    /**
     * 查询对账记录
     * @param payChannel 支付渠道
     * @param billDate 账单日期
     * @param status 对账状态
     * @return 对账记录列表
     */
    List<ReconciliationRecord> queryReconciliationRecords(String payChannel, Date billDate, String status);

    /**
     * 生成对账报告
     * @param payChannel 支付渠道
     * @param billDate 账单日期
     * @return 对账报告内容
     */
    String generateReconciliationReport(String payChannel, Date billDate);
}
