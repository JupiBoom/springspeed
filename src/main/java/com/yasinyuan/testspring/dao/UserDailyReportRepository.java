package com.yasinyuan.testspring.dao;

import com.yasinyuan.testspring.model.UserDailyReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserDailyReportRepository extends JpaRepository<UserDailyReport, Long> {
    UserDailyReport findByReportDate(Date reportDate);
    List<UserDailyReport> findByReportDateBetween(Date startDate, Date endDate);
    void deleteByReportDateBefore(Date date);
}
