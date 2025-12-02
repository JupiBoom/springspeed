package com.ecommerce.service;

import com.ecommerce.entity.UserBehaviorDailyReport;
import com.ecommerce.entity.FunnelAnalysisResult;
import com.ecommerce.entity.UserSegment;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.Date;
import java.util.List;

public interface ReportService {

    /**
     * 生成用户行为日报
     * @param reportDate 报告日期
     * @return 用户行为日报
     */
    UserBehaviorDailyReport generateUserBehaviorDailyReport(Date reportDate);

    /**
     * 获取用户行为日报列表
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 用户行为日报列表
     */
    List<UserBehaviorDailyReport> getUserBehaviorDailyReports(Date startDate, Date endDate);

    /**
     * 获取最新的用户行为日报
     * @return 最新的用户行为日报
     */
    UserBehaviorDailyReport getLatestUserBehaviorDailyReport();

    /**
     * 生成漏斗分析报表
     * @param funnelId 漏斗ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 漏斗分析结果列表
     */
    List<FunnelAnalysisResult> generateFunnelAnalysisReport(Long funnelId, Date startDate, Date endDate);

    /**
     * 导出用户分群明细到Excel
     * @param segmentName 分群名称
     * @return Excel工作簿
     */
    XSSFWorkbook exportUserSegmentDetailsToExcel(String segmentName);

    /**
     * 导出用户行为日报到Excel
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return Excel工作簿
     */
    XSSFWorkbook exportUserBehaviorDailyReportsToExcel(Date startDate, Date endDate);

    /**
     * 导出漏斗分析报表到Excel
     * @param funnelId 漏斗ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return Excel工作簿
     */
    XSSFWorkbook exportFunnelAnalysisReportToExcel(Long funnelId, Date startDate, Date endDate);
}
