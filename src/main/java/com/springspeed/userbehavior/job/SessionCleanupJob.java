package com.springspeed.userbehavior.job;

import com.springspeed.userbehavior.service.UserSessionService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 会话清理定时任务
 * 定期清理超时的用户会话
 */
@Component
public class SessionCleanupJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(SessionCleanupJob.class);

    @Autowired
    private UserSessionService userSessionService;

    /**
     * 执行会话清理任务
     * @param context 任务执行上下文
     * @throws JobExecutionException 任务执行异常
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("开始执行会话清理任务");

        try {
            // 清理超时的用户会话
            int cleanupCount = userSessionService.cleanupTimeoutUserSessions();
            logger.info("会话清理任务执行完成，共清理 {} 个超时会话", cleanupCount);
        } catch (Exception e) {
            logger.error("会话清理任务执行失败", e);
            throw new JobExecutionException("会话清理任务执行失败", e);
        }
    }
}