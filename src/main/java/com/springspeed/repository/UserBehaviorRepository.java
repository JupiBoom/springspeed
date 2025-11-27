package com.springspeed.repository;

import com.springspeed.model.UserBehavior;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User Behavior Repository
 */
@Repository
public interface UserBehaviorRepository extends JpaRepository<UserBehavior, Long> {

    List<UserBehavior> findByUserId(Long userId);

    List<UserBehavior> findByUserIdAndBehaviorType(Long userId, Integer behaviorType);

    List<UserBehavior> findByProductId(Long productId);

    @Query("SELECT ub FROM UserBehavior ub WHERE ub.userId = :userId ORDER BY ub.createdTime DESC")
    List<UserBehavior> findLatestUserBehaviors(@Param("userId") Long userId);

    @Query("SELECT ub FROM UserBehavior ub WHERE ub.userId = :userId AND ub.behaviorType IN :behaviorTypes ORDER BY ub.createdTime DESC")
    List<UserBehavior> findUserBehaviorsByTypes(@Param("userId") Long userId, @Param("behaviorTypes") List<Integer> behaviorTypes);

    @Query("SELECT ub.productId, SUM(ub.behaviorWeight) as totalWeight FROM UserBehavior ub WHERE ub.userId = :userId GROUP BY ub.productId ORDER BY totalWeight DESC")
    List<Object[]> findUserProductInterests(@Param("userId") Long userId);

    @Query("SELECT ub.userId, COUNT(ub.id) as behaviorCount FROM UserBehavior ub WHERE ub.behaviorType = :behaviorType GROUP BY ub.userId ORDER BY behaviorCount DESC")
    List<Object[]> findTopUsersByBehaviorType(@Param("behaviorType") Integer behaviorType);

    @Query("SELECT ub.productId, COUNT(ub.id) as behaviorCount FROM UserBehavior ub WHERE ub.behaviorType = :behaviorType GROUP BY ub.productId ORDER BY behaviorCount DESC")
    List<Object[]> findTopProductsByBehaviorType(@Param("behaviorType") Integer behaviorType);

    @Query("SELECT COUNT(ub) FROM UserBehavior ub WHERE ub.behaviorType = :behaviorType AND ub.createdTime >= :startDate AND ub.createdTime <= :endDate")
    Long countBehaviorsByTypeAndDateRange(@Param("behaviorType") Integer behaviorType, @Param("startDate") String startDate, @Param("endDate") String endDate);

    @Query("SELECT ub FROM UserBehavior ub WHERE ub.userId = :userId AND ub.productId = :productId AND ub.behaviorType = :behaviorType")
    UserBehavior findUserBehavior(@Param("userId") Long userId, @Param("productId") Long productId, @Param("behaviorType") Integer behaviorType);
}
