package com.yasinyuan.testspring.dao;

import com.yasinyuan.testspring.model.FunnelConversionResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FunnelConversionResultRepository extends JpaRepository<FunnelConversionResult, Long> {
    List<FunnelConversionResult> findByFunnelIdAndDate(Long funnelId, Date date);
    List<FunnelConversionResult> findByDate(Date date);
    void deleteByDateBefore(Date date);
}
