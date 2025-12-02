package com.yasinyuan.testspring.web;

import com.yasinyuan.testspring.model.UserDailyReport;
import com.yasinyuan.testspring.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    // 日报相关接口
    @PostMapping("/daily/generate")
    public UserDailyReport generateDailyReport(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date reportDate) {
        return reportService.generateDailyReport(reportDate);
    }

    @GetMapping("/daily")
    public UserDailyReport getDailyReport(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date reportDate) {
        return reportService.getDailyReport(reportDate);
    }

    @GetMapping("/daily/range")
    public List<UserDailyReport> getDailyReports(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return reportService.getDailyReports(startDate, endDate);
    }

    // 漏斗分析报表接口
    @GetMapping("/funnel")
    public Map<String, Object> getFunnelReport(
            @RequestParam Long funnelId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return reportService.getFunnelReport(funnelId, startDate, endDate);
    }

    // 用户分群报表接口
    @GetMapping("/user-segment")
    public Map<String, Object> getUserSegmentReport() {
        return reportService.getUserSegmentReport();
    }

    // 数据导出接口
    @GetMapping("/export/daily")
    public void exportDailyReport(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            HttpServletResponse response) throws IOException {
        reportService.exportDailyReport(startDate, endDate, response);
    }

    @GetMapping("/export/funnel")
    public void exportFunnelReport(
            @RequestParam Long funnelId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            HttpServletResponse response) throws IOException {
        reportService.exportFunnelReport(funnelId, startDate, endDate, response);
    }

    @GetMapping("/export/user-segment")
    public void exportUserSegmentReport(
            @RequestParam String segmentType,
            HttpServletResponse response) throws IOException {
        reportService.exportUserSegmentReport(segmentType, response);
    }
}
