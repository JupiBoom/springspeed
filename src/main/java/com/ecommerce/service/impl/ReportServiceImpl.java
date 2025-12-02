package com.ecommerce.service.impl;

import com.ecommerce.entity.UserBehaviorDailyReport;
import com.ecommerce.entity.FunnelAnalysisResult;
import com.ecommerce.repository.UserBehaviorDailyReportRepository;
import com.ecommerce.repository.FunnelAnalysisResultRepository;
import com.ecommerce.service.ReportService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ReportServiceImpl implements ReportService {

    @Autowired
    private UserBehaviorDailyReportRepository dailyReportRepository;

    @Autowired
    private FunnelAnalysisResultRepository funnelAnalysisResultRepository;

    @Override
    public UserBehaviorDailyReport generateUserBehaviorDailyReport(Date reportDate) {
        // 这里应该根据业务逻辑生成用户行为日报
        // 暂时返回模拟数据，实际项目中需要从数据库查询统计数据
        UserBehaviorDailyReport report = new UserBehaviorDailyReport();
        report.setReportDate(reportDate);
        report.setTotalUsers(10000);
        report.setNewUsers(500);
        report.setActiveUsers(3000);
        report.setPageViews(50000);
        report.setUniquePageViews(25000);
        report.setAvgSessionDuration(180.5);
        report.setBounceRate(0.45);
        report.setConversionRate(0.03);
        report.setCreatedTime(new Date());
        
        // 保存到数据库
        return dailyReportRepository.save(report);
    }

    @Override
    public List<UserBehaviorDailyReport> getUserBehaviorDailyReports(Date startDate, Date endDate) {
        return dailyReportRepository.findByReportDateBetween(startDate, endDate);
    }

    @Override
    public UserBehaviorDailyReport getLatestUserBehaviorDailyReport() {
        return dailyReportRepository.findTopByOrderByReportDateDesc();
    }

    @Override
    public List<FunnelAnalysisResult> generateFunnelAnalysisReport(Long funnelId, Date startDate, Date endDate) {
        // 这里应该根据业务逻辑生成漏斗分析报表
        // 暂时返回模拟数据，实际项目中需要从数据库查询统计数据
        return funnelAnalysisResultRepository.findByFunnelIdAndAnalysisDateBetween(funnelId, startDate, endDate);
    }

    @Override
    public XSSFWorkbook exportUserBehaviorDailyReportsToExcel(Date startDate, Date endDate) throws IOException {
        List<UserBehaviorDailyReport> reports = getUserBehaviorDailyReports(startDate, endDate);
        
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("用户行为日报");
        
        // 创建标题行
        Row headerRow = sheet.createRow(0);
        String[] headers = {"报告日期", "总用户数", "新用户数", "活跃用户数", "页面浏览量", "独立页面浏览量", "平均会话时长", "跳出率", "转化率"};
        
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            
            // 设置标题样式
            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            style.setFont(font);
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            style.setBorderTop(BorderStyle.THIN);
            style.setBorderBottom(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);
            cell.setCellStyle(style);
        }
        
        // 填充数据
        for (int i = 0; i < reports.size(); i++) {
            UserBehaviorDailyReport report = reports.get(i);
            Row dataRow = sheet.createRow(i + 1);
            
            dataRow.createCell(0).setCellValue(report.getReportDate());
            dataRow.createCell(1).setCellValue(report.getTotalUsers());
            dataRow.createCell(2).setCellValue(report.getNewUsers());
            dataRow.createCell(3).setCellValue(report.getActiveUsers());
            dataRow.createCell(4).setCellValue(report.getPageViews());
            dataRow.createCell(5).setCellValue(report.getUniquePageViews());
            dataRow.createCell(6).setCellValue(report.getAvgSessionDuration());
            dataRow.createCell(7).setCellValue(report.getBounceRate());
            dataRow.createCell(8).setCellValue(report.getConversionRate());
            
            // 设置数据样式
            for (int j = 0; j < headers.length; j++) {
                Cell cell = dataRow.getCell(j);
                CellStyle style = workbook.createCellStyle();
                style.setAlignment(HorizontalAlignment.CENTER);
                style.setVerticalAlignment(VerticalAlignment.CENTER);
                style.setBorderTop(BorderStyle.THIN);
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderLeft(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                cell.setCellStyle(style);
            }
        }
        
        // 自动调整列宽
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
        
        return workbook;
    }

    @Override
    public XSSFWorkbook exportFunnelAnalysisReportToExcel(Long funnelId, Date startDate, Date endDate) throws IOException {
        List<FunnelAnalysisResult> results = generateFunnelAnalysisReport(funnelId, startDate, endDate);
        
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("漏斗分析报表");
        
        // 创建标题行
        Row headerRow = sheet.createRow(0);
        String[] headers = {"分析日期", "步骤索引", "步骤名称", "用户数", "转化率", "总体转化率"};
        
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            
            // 设置标题样式
            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            style.setFont(font);
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            style.setBorderTop(BorderStyle.THIN);
            style.setBorderBottom(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);
            cell.setCellStyle(style);
        }
        
        // 填充数据
        for (int i = 0; i < results.size(); i++) {
            FunnelAnalysisResult result = results.get(i);
            Row dataRow = sheet.createRow(i + 1);
            
            dataRow.createCell(0).setCellValue(result.getAnalysisDate());
            dataRow.createCell(1).setCellValue(result.getStepIndex());
            dataRow.createCell(2).setCellValue(result.getStepName());
            dataRow.createCell(3).setCellValue(result.getUserCount());
            dataRow.createCell(4).setCellValue(result.getConversionRate());
            dataRow.createCell(5).setCellValue(result.getOverallConversionRate());
            
            // 设置数据样式
            for (int j = 0; j < headers.length; j++) {
                Cell cell = dataRow.getCell(j);
                CellStyle style = workbook.createCellStyle();
                style.setAlignment(HorizontalAlignment.CENTER);
                style.setVerticalAlignment(VerticalAlignment.CENTER);
                style.setBorderTop(BorderStyle.THIN);
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderLeft(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                cell.setCellStyle(style);
            }
        }
        
        // 自动调整列宽
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
        
        return workbook;
    }

    @Override
    public XSSFWorkbook exportUserSegmentDetailsToExcel(String segmentName) throws IOException {
        // 这里应该根据业务逻辑导出用户分群明细到Excel
        // 暂时返回空的Workbook，实际项目中需要实现具体的导出逻辑
        return new XSSFWorkbook();
    }
}
