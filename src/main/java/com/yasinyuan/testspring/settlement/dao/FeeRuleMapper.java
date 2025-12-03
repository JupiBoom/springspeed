package com.yasinyuan.testspring.settlement.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yasinyuan.testspring.settlement.model.FeeRule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * 手续费规则Mapper接口
 */
@Mapper
public interface FeeRuleMapper extends BaseMapper<FeeRule> {
    
    /**
     * 根据支付渠道和交易类型查询有效的手续费规则
     * @param paymentChannel 支付渠道
     * @param tradeType 交易类型
     * @param currentTime 当前时间
     * @return 手续费规则
     */
    FeeRule selectEffectiveRule(@Param("paymentChannel") String paymentChannel, 
                               @Param("tradeType") String tradeType, 
                               @Param("currentTime") LocalDateTime currentTime);
}