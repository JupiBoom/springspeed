package com.springspeed.repository;

import com.springspeed.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User Repository
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.status = 1 ORDER BY u.lastActiveTime DESC")
    List<User> findActiveUsersOrderByLastActive();

    @Query("SELECT u FROM User u WHERE u.rfmScore >= :minScore ORDER BY u.rfmScore DESC")
    List<User> findHighValueUsers(@Param("minScore") Integer minScore);

    @Query("SELECT u FROM User u WHERE u.interestTags LIKE %:tag%")
    List<User> findUsersByInterestTag(@Param("tag") String tag);

    @Query("SELECT COUNT(u) FROM User u WHERE u.createdTime >= :startDate AND u.createdTime <= :endDate")
    Long countNewUsersByDateRange(@Param("startDate") String startDate, @Param("endDate") String endDate);
}
