package com.yasinyuan.testspring.service.impl;

import com.yasinyuan.testspring.dao.UserDailyReportRepository;
import com.yasinyuan.testspring.model.UserBehavior;
import com.yasinyuan.testspring.model.UserDailyReport;
import com.yasinyuan.testspring.service.FunnelAnalysisService;
import com.yasinyuan.testspring.service.ReportService;
import com.yasinyuan.testspring.service.UserSegmentationService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private UserDailyReportRepository userDailyReportRepository;

    @Autowired
    private com.yasinyuan.testspring.dao.UserBehaviorRepository userBehaviorRepository;

    @Autowired
    private FunnelAnalysisService funnelAnalysisService;

    @Autowired
    private UserSegmentationService userSegmentationService;

    @Override
    @Transactional
    public UserDailyReport generateDailyReport(Date reportDate) {
        // 计算当天开始和结束时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(reportDate);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date startOfDay = calendar.getTime();

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date endOfDay = calendar.getTime();

        // 获取当天所有用户行为
        List<UserBehavior> dailyBehaviors = userBehaviorRepository.findByCreateTimeBetween(startOfDay, endOfDay);

        // 查询已有报表，存在则更新
        UserDailyReport report = userDailyReportRepository.findByReportDate(reportDate);
        if (report == null) {
            report = new UserDailyReport();
            report.setReportDate(reportDate);
        }

        // 计算新用户数（首次访问）
        Set<Long> allUsers = dailyBehaviors.stream().map(UserBehavior::getUserId).collect(Collectors.toSet());
        long newUsers = allUsers.stream().filter(userId -> {
            Page<UserBehavior> userBehaviorsPage = userBehaviorRepository.findByUserId(userId, new PageRequest(0, Integer.MAX_VALUE));
            List<UserBehavior> userBehaviors = userBehaviorsPage.getContent();
            return userBehaviors.stream()
                    .min(Comparator.comparing(UserBehavior::getCreateTime))
                    .map(b -> b.getCreateTime().compareTo(startOfDay) >= 0)
                    .orElse(false);
        }).count();

        // 活跃用户数
        long activeUsers = allUsers.size();

        // 总访问次数（会话数）
        long totalVisits = dailyBehaviors.stream().map(UserBehavior::getSessionId).distinct().count();

        // 页面浏览量
        long pageViews = dailyBehaviors.size();

        // 平均会话时长
        long avgSessionDuration = calculateAvgSessionDuration(dailyBehaviors);

        // 按事件类型统计
        Map<String, Long> eventCountMap = dailyBehaviors.stream()
                .collect(Collectors.groupingBy(UserBehavior::getBehaviorType, Collectors.counting()));

        // 订单总金额
        double orderAmount = dailyBehaviors.stream()
                .filter(b -> "order".equals(b.getBehaviorType()))
                .mapToDouble(b -> {
                    if (b.getExtInfo() != null && b.getExtInfo().containsKey("amount")) {
                        Object amountObj = b.getExtInfo().get("amount");
                        if (amountObj instanceof Number) {
                            return ((Number) amountObj).doubleValue();
                        }
                    }
                    return 0.0;
                })
                .sum();

        // 设置报表数据
        report.setNewUsers(newUsers);
        report.setActiveUsers(activeUsers);
        report.setTotalVisits(totalVisits);
        report.setPageViews(pageViews);
        report.setAvgSessionDuration(avgSessionDuration);
        report.setBrowseCount(eventCountMap.getOrDefault("browse", 0L));
        report.setClickCount(eventCountMap.getOrDefault("click", 0L));
        report.setAddCartCount(eventCountMap.getOrDefault("add_cart", 0L));
        report.setOrderCount(eventCountMap.getOrDefault("order", 0L));
        report.setOrderAmount(orderAmount);
        report.setCreateTime(new Date());

        return userDailyReportRepository.save(report);
    }

    private long calculateAvgSessionDuration(List<UserBehavior> behaviors) {
        Map<String, List<UserBehavior>> sessionMap = behaviors.stream()
                .collect(Collectors.groupingBy(UserBehavior::getSessionId));

        long totalDuration = 0;
        int sessionCount = 0;

        for (List<UserBehavior> sessionBehaviors : sessionMap.values()) {
            if (sessionBehaviors.size() < 2) continue;

            Date firstTime = sessionBehaviors.stream()
                    .min(Comparator.comparing(UserBehavior::getCreateTime))
                    .map(UserBehavior::getCreateTime)
                    .orElse(new Date());
            Date lastTime = sessionBehaviors.stream()
                    .max(Comparator.comparing(UserBehavior::getCreateTime))
                    .map(UserBehavior::getCreateTime)
                    .orElse(new Date());

            totalDuration += (lastTime.getTime() - firstTime.getTime()) / 1000;
            sessionCount++;
        }

        return sessionCount > 0 ? totalDuration / sessionCount : 0;
    }

    @Override
    public UserDailyReport getDailyReport(Date reportDate) {
        return userDailyReportRepository.findByReportDate(reportDate);
    }

    @Override
    public List<UserDailyReport> getDailyReports(Date startDate, Date endDate) {
        return userDailyReportRepository.findByReportDateBetween(startDate, endDate);
    }

    @Override
    public Map<String, Object> getFunnelReport(Long funnelId, Date startDate, Date endDate) {
        Map<String, Object> report = new HashMap<>();
        report.put("funnelId", funnelId);
        report.put("startDate", startDate);
        report.put("endDate", endDate);

        // 获取每日转化率
        List<Map<String, Object>> dailyData = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        while (calendar.getTime().compareTo(endDate) <= 0) {
            Date currentDate = calendar.getTime();
            List<?> conversionResult = funnelAnalysisService.getFunnelConversionResult(funnelId, currentDate);
            dailyData.add(Map.of(
                    "date", currentDate,
                    "conversionData", conversionResult
            ));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        report.put("dailyData", dailyData);
        return report;
    }

    @Override
    public Map<String, Object> getUserSegmentReport() {
        Map<String, Object> report = new HashMap<>();
        report.put("rfmSegmentCount", userSegmentationService.countUsersByRfmSegment());
        report.put("lifecycleStageCount", userSegmentationService.countUsersByLifecycleStage());
        return report;
    }

    @Override
    public void exportDailyReport(Date startDate, Date endDate, HttpServletResponse response) throws IOException {
        List<UserDailyReport> reports = getDailyReports(startDate, endDate);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("用户行为日报");

        // 创建表头
        String[] headers = {"日期", "新用户数", "活跃用户数", "总访问次数", "页面浏览量", "平均会话时长(秒)", "浏览次数", "点击次数", "加购次数", "下单次数", "订单总金额"};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(createHeaderCellStyle(workbook));
        }

        // 填充数据
        int rowNum = 1;
        for (UserDailyReport report : reports) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(report.getReportDate().toString());
            row.createCell(1).setCellValue(report.getNewUsers());
            row.createCell(2).setCellValue(report.getActiveUsers());
            row.createCell(3).setCellValue(report.getTotalVisits());
            row.createCell(4).setCellValue(report.getPageViews());
            row.createCell(5).setCellValue(report.getAvgSessionDuration());
            row.createCell(6).setCellValue(report.getBrowseCount());
            row.createCell(7).setCellValue(report.getClickCount());
            row.createCell(8).setCellValue(report.getAddCartCount());
            row.createCell(9).setCellValue(report.getOrderCount());
            row.createCell(10).setCellValue(report.getOrderAmount());
        }

        // 自动调整列宽
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=user_daily_report_" + startDate + "_" + endDate + ".xlsx");

        // 写入输出流
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.flush();
    }

    @Override
    public void exportFunnelReport(Long funnelId, Date startDate, Date endDate, HttpServletResponse response) throws IOException {
        // 实现漏斗报表导出逻辑
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("漏斗分析报表");

        // 创建表头和填充数据...

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=funnel_report_" + funnelId + "_" + startDate + "_" + endDate + ".xlsx");

        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.flush();
    }

    @Override
    public void exportUserSegmentReport(String segmentType, HttpServletResponse response) throws IOException {
        Map<String, Long> segmentData;
        if ("rfm".equals(segmentType)) {
            segmentData = userSegmentationService.countUsersByRfmSegment();
        } else {
            segmentData = userSegmentationService.countUsersByLifecycleStage();
        }

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("用户分群报表");

        // 创建表头
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("分群类型");
        headerRow.createCell(1).setCellValue("用户数量");

        // 填充数据
        int rowNum = 1;
        for (Map.Entry<String, Long> entry : segmentData.entrySet()) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(entry.getKey());
            row.createCell(1).setCellValue(entry.getValue());
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=user_segment_report_" + segmentType + ".xlsx");

        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.flush();
    }

    private CellStyle createHeaderCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }
}
