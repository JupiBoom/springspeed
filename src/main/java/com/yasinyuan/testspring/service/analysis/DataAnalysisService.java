package com.yasinyuan.testspring.service.analysis;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface DataAnalysisService {
    
    /**
     * 获取销售排行数据
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param topN 前N名
     * @return 销售排行列表，包含商品ID、商品名称、销售数量、销售金额等信息
     */
    List<Map<String, Object>> getSalesRanking(Date startDate, Date endDate, Integer topN);
    
    /**
     * 获取库存周转率数据
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 库存周转率列表，包含商品ID、商品名称、平均库存、销售数量、周转率等信息
     */
    List<Map<String, Object>> getInventoryTurnover(Date startDate, Date endDate);
    
    /**
     * 获取商品上下架效率数据
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 上下架效率列表，包含商品ID、商品名称、申请时间、上架时间、下架时间、上架时长、下架时长等信息
     */
    List<Map<String, Object>> getProductListingEfficiency(Date startDate, Date endDate);
    
    /**
     * 获取商品销售趋势数据
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param period 时间周期：天、周、月
     * @return 销售趋势列表，包含时间点、销售数量、销售金额等信息
     */
    List<Map<String, Object>> getSalesTrend(Date startDate, Date endDate, String period);
    
    /**
     * 获取库存预警统计数据
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 库存预警统计列表，包含商品ID、商品名称、预警次数、平均库存等信息
     */
    List<Map<String, Object>> getInventoryWarningStats(Date startDate, Date endDate);
}
