package com.ecommerce.job;

import com.ecommerce.entity.UserBehaviorDailyReport;
import com.ecommerce.service.ReportService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;

public class DailyReportJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(DailyReportJob.class);

    @Autowired
    private ReportService reportService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("开始自动生成用户行为日报");
        
        try {
            // 生成昨天的日报（定时任务通常在每天凌晨执行，生成前一天的日报）
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            Date reportDate = calendar.getTime();
            
            UserBehaviorDailyReport report = reportService.generateUserBehaviorDailyReport(reportDate);
            logger.info("用户行为日报生成成功，报告日期：{}", report.getReportDate());
        } catch (Exception e) {
            logger.error("用户行为日报生成失败：", e);
            throw new JobExecutionException("用户行为日报生成失败", e);
        }
    }
}
