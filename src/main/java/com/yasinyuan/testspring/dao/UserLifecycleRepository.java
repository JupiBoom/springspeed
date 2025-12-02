package com.yasinyuan.testspring.dao;

import com.yasinyuan.testspring.model.UserLifecycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 用户生命周期JPA Repository
 * @author yasinyuan
 * @date 2025-05-02
 */
public interface UserLifecycleRepository extends JpaRepository<UserLifecycle, Long> {

    /**
     * 根据用户ID查询生命周期信息
     * @param userId 用户ID
     * @return 生命周期信息
     */
    UserLifecycle findByUserId(Long userId);

    /**
     * 根据生命周期阶段查询用户列表
     * @param lifeCycleStage 生命周期阶段
     * @return 用户生命周期列表
     */
    List<UserLifecycle> findByLifeCycleStage(String lifeCycleStage);

    /**
     * 按生命周期阶段分组统计用户数量
     * @return 阶段统计结果
     */
    @Query(value = "SELECT life_cycle_stage, COUNT(*) as count FROM user_lifecycle GROUP BY life_cycle_stage", nativeQuery = true)
    List<Object[]> countByLifeCycleStage();

    /**
     * 查询沉默天数大于等于指定值的用户
     * @param silentDays 沉默天数阈值
     * @return 用户生命周期列表
     */
    List<UserLifecycle> findBySilentDaysGreaterThanEqual(Integer silentDays);
}
