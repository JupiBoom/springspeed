package com.yasinyuan.testspring.service;

import com.yasinyuan.testspring.model.UserDailyReport;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ReportService {
    // 日报相关
    UserDailyReport generateDailyReport(Date reportDate);
    UserDailyReport getDailyReport(Date reportDate);
    List<UserDailyReport> getDailyReports(Date startDate, Date endDate);

    // 漏斗分析报表
    Map<String, Object> getFunnelReport(Long funnelId, Date startDate, Date endDate);

    // 用户分群报表
    Map<String, Object> getUserSegmentReport();

    // 数据导出
    void exportDailyReport(Date startDate, Date endDate, HttpServletResponse response);
    void exportFunnelReport(Long funnelId, Date startDate, Date endDate, HttpServletResponse response);
    void exportUserSegmentReport(String segmentType, HttpServletResponse response);
}
