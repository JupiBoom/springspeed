package com.yasinyuan.testspring.quartz;

import com.yasinyuan.testspring.service.ReportService;
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
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            // 获取前一天的日期
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -1);
            Date reportDate = calendar.getTime();

            logger.info("开始自动生成日报，日期：{}", reportDate);
            reportService.generateDailyReport(reportDate);
            logger.info("日报自动生成完成，日期：{}", reportDate);
        } catch (Exception e) {
            logger.error("自动生成日报失败", e);
            throw new JobExecutionException(e);
        }
    }
}
