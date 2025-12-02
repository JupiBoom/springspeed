package com.ecommerce.repository;

import com.ecommerce.entity.Funnel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FunnelRepository extends JpaRepository<Funnel, Long> {

    /**
     * 查询所有激活的漏斗
     */
    List<Funnel> findByIsActiveTrue();

    /**
     * 根据漏斗名称查询漏斗
     */
    Funnel findByFunnelName(String funnelName);

    /**
     * 根据漏斗名称模糊查询漏斗
     */
    List<Funnel> findByFunnelNameContaining(String funnelName);
}
