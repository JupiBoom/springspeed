package com.yasinyuan.testspring.quartz;

import com.yasinyuan.testspring.service.UserSegmentationService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;

public class UserSegmentUpdateJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(UserSegmentUpdateJob.class);

    @Autowired
    private UserSegmentationService userSegmentationService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            logger.info("开始自动更新用户分群数据");

            // 重新计算所有用户的RFM模型，使用过去30天的数据
            Date endDate = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, -30);
            Date startDate = calendar.getTime();
            userSegmentationService.calculateRfmModel(startDate, endDate);
            logger.info("RFM模型计算完成");

            // 更新用户生命周期划分
            userSegmentationService.calculateUserLifecycle();
            logger.info("用户生命周期划分完成");

            logger.info("用户分群数据自动更新完成");
        } catch (Exception e) {
            logger.error("自动更新用户分群数据失败", e);
            throw new JobExecutionException(e);
        }
    }
}
