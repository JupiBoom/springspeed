package com.yasinyuan.testspring.dao;

import com.yasinyuan.testspring.model.FunnelConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 漏斗配置JPA Repository
 * @author yasinyuan
 * @date 2025-05-02
 */
public interface FunnelConfigRepository extends JpaRepository<FunnelConfig, Long> {

    /**
     * 查询启用的漏斗配置
     * @return 漏斗配置列表
     */
    List<FunnelConfig> findByStatus(Integer status);

    /**
     * 根据漏斗名称查询配置
     * @param funnelName 漏斗名称
     * @return 漏斗配置
     */
    FunnelConfig findByFunnelName(String funnelName);
}
