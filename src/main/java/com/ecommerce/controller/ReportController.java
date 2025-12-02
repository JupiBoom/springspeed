package com.ecommerce.controller;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.ecommerce.entity.UserBehaviorDailyReport;
import com.ecommerce.entity.FunnelAnalysisResult;
import com.ecommerce.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    /**
     * 生成用户行为日报
     * @param reportDate 报告日期
     * @return 生成的日报
     */
    @PostMapping("/daily")
    public ResponseEntity<UserBehaviorDailyReport> generateDailyReport(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date reportDate) {
        UserBehaviorDailyReport report = reportService.generateUserBehaviorDailyReport(reportDate);
        return ResponseEntity.ok(report);
    }

    /**
     * 获取指定时间范围内的用户行为日报
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 日报列表
     */
    @GetMapping("/daily")
    public ResponseEntity<List<UserBehaviorDailyReport>> getDailyReports(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        List<UserBehaviorDailyReport> reports = reportService.getUserBehaviorDailyReports(startDate, endDate);
        return ResponseEntity.ok(reports);
    }

    /**
     * 获取最新的用户行为日报
     * @return 最新的日报
     */
    @GetMapping("/daily/latest")
    public ResponseEntity<UserBehaviorDailyReport> getLatestDailyReport() {
        UserBehaviorDailyReport report = reportService.getLatestUserBehaviorDailyReport();
        return ResponseEntity.ok(report);
    }

    /**
     * 生成漏斗分析报表
     * @param funnelId 漏斗ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 漏斗分析结果列表
     */
    @GetMapping("/funnel/{funnelId}")
    public ResponseEntity<List<FunnelAnalysisResult>> generateFunnelReport(
            @PathVariable Long funnelId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        List<FunnelAnalysisResult> results = reportService.generateFunnelAnalysisReport(funnelId, startDate, endDate);
        return ResponseEntity.ok(results);
    }

    /**
     * 导出用户行为日报到Excel
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return Excel文件字节数组
     * @throws IOException IO异常
     */
    @GetMapping("/export/daily")
    public ResponseEntity<byte[]> exportDailyReportsToExcel(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) throws IOException {
        XSSFWorkbook workbook = reportService.exportUserBehaviorDailyReportsToExcel(startDate, endDate);
        
        // 将Workbook转换为字节数组
        byte[] excelBytes;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            workbook.write(outputStream);
            excelBytes = outputStream.toByteArray();
        }
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "用户行为日报.xlsx");
        
        return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
    }

    /**
     * 导出漏斗分析报表到Excel
     * @param funnelId 漏斗ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return Excel文件字节数组
     * @throws IOException IO异常
     */
    @GetMapping("/export/funnel/{funnelId}")
    public ResponseEntity<byte[]> exportFunnelAnalysisToExcel(
            @PathVariable Long funnelId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) throws IOException {
        XSSFWorkbook workbook = reportService.exportFunnelAnalysisReportToExcel(funnelId, startDate, endDate);
        
        // 将Workbook转换为字节数组
        byte[] excelBytes;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            workbook.write(outputStream);
            excelBytes = outputStream.toByteArray();
        }
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "漏斗分析报表.xlsx");
        
        return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
    }

    /**
     * 导出用户分群明细到Excel
     * @param segmentName 分群名称
     * @return Excel文件字节数组
     * @throws IOException IO异常
     */
    @GetMapping("/export/segments/{segmentName}")
    public ResponseEntity<byte[]> exportUserSegmentsToExcel(@PathVariable String segmentName) throws IOException {
        XSSFWorkbook workbook = reportService.exportUserSegmentDetailsToExcel(segmentName);
        
        // 将Workbook转换为字节数组
        byte[] excelBytes;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            workbook.write(outputStream);
            excelBytes = outputStream.toByteArray();
        }
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "用户分群明细.xlsx");
        
        return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
    }
}
