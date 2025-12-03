package com.yasinyuan.testspring.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 数据分析服务接口
 */
public interface DataAnalysisService {

    /**
     * 获取商品销售排行
     * @param topN 获取前N条数据
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 销售排行列表，包含商品ID、商品名称、销售数量、销售金额
     */
    List<Map<String, Object>> getProductSalesRanking(Integer topN, Date startTime, Date endTime);

    /**
     * 库存周转率分析
     * @param categoryId 分类ID（可选，为空则查询全部）
     * @param brandId 品牌ID（可选，为空则查询全部）
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 库存周转率数据，包含商品ID、商品名称、平均库存、销售成本、周转率
     */
    List<Map<String, Object>> getInventoryTurnoverAnalysis(Long categoryId, Long brandId, Date startTime, Date endTime);

    /**
     * 商品上下架效率统计
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 上下架统计数据，包含总商品数、上架商品数、下架商品数、平均上架时长、平均审核时长
     */
    Map<String, Object> getProductShelfEfficiency(Date startTime, Date endTime);

    /**
     * 获取商品销售趋势
     * @param productId 商品ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param periodType 周期类型：day/week/month
     * @return 销售趋势数据
     */
    List<Map<String, Object>> getProductSalesTrend(Long productId, Date startTime, Date endTime, String periodType);
}