package com.yasinyuan.testspring.dao;

import com.yasinyuan.testspring.model.FunnelStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FunnelStepRepository extends JpaRepository<FunnelStep, Long> {
    List<FunnelStep> findByFunnelIdOrderByStepAsc(Long funnelId);
    void deleteByFunnelId(Long funnelId);
}
