package com.yasinyuan.testspring.dao;

import com.yasinyuan.testspring.model.UserRfm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * RFM用户分群JPA Repository
 * @author yasinyuan
 * @date 2025-05-02
 */
public interface UserRfmRepository extends JpaRepository<UserRfm, Long> {

    /**
     * 根据用户ID查询RFM信息
     * @param userId 用户ID
     * @return RFM信息
     */
    UserRfm findByUserId(Long userId);

    /**
     * 根据用户群体标签查询用户列表
     * @param userSegment 用户群体标签
     * @return 用户RFM列表
     */
    List<UserRfm> findByUserSegment(String userSegment);

    /**
     * 按用户群体分组统计用户数量
     * @return 群体统计结果
     */
    @Query(value = "SELECT user_segment, COUNT(*) as count FROM user_rfm GROUP BY user_segment", nativeQuery = true)
    List<Object[]> countByUserSegment();

    /**
     * 查询R分大于等于指定值的用户
     * @param rScore R分阈值
     * @return 用户RFM列表
     */
    List<UserRfm> findByRScoreGreaterThanEqual(@Param("rScore") Integer rScore);

    /**
     * 查询F分大于等于指定值的用户
     * @param fScore F分阈值
     * @return 用户RFM列表
     */
    List<UserRfm> findByFScoreGreaterThanEqual(@Param("fScore") Integer fScore);

    /**
     * 查询M分大于等于指定值的用户
     * @param mScore M分阈值
     * @return 用户RFM列表
     */
    List<UserRfm> findByMScoreGreaterThanEqual(@Param("mScore") Integer mScore);
}
