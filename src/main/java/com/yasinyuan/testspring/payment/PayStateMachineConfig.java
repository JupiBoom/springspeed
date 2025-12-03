package com.yasinyuan.testspring.payment;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.transition.Transition;

import java.util.EnumSet;

/**
 * 支付状态机配置类
 */
@Configuration
@EnableStateMachine
public class PayStateMachineConfig extends StateMachineConfigurerAdapter<PayStatus, PayEvent> {

    /**
     * 配置状态
     */
    @Override
    public void configure(StateMachineStateConfigurer<PayStatus, PayEvent> states) throws Exception {
        states
            .withStates()
            .initial(PayStatus.WAITING_PAY)  // 初始状态：待支付
            .states(EnumSet.allOf(PayStatus.class));  // 所有状态
    }

    /**
     * 配置状态转换
     */
    @Override
    public void configure(StateMachineTransitionConfigurer<PayStatus, PayEvent> transitions) throws Exception {
        // 待支付 -> 支付中
        transitions
            .withExternal()
            .source(PayStatus.WAITING_PAY)
            .target(PayStatus.PAYING)
            .event(PayEvent.START_PAY)
            .and()
            // 支付中 -> 支付成功
            .withExternal()
            .source(PayStatus.PAYING)
            .target(PayStatus.PAY_SUCCESS)
            .event(PayEvent.PAY_SUCCESS_EVENT)
            .and()
            // 支付中 -> 支付失败
            .withExternal()
            .source(PayStatus.PAYING)
            .target(PayStatus.PAY_FAILED)
            .event(PayEvent.PAY_FAILED_EVENT)
            .and()
            // 待支付 -> 支付关闭（超时未支付）
            .withExternal()
            .source(PayStatus.WAITING_PAY)
            .target(PayStatus.PAY_CLOSED)
            .event(PayEvent.PAY_TIMEOUT);
    }

    /**
     * 配置状态机监听器
     */
    @Override
    public void configure(StateMachineConfigurationConfigurer<PayStatus, PayEvent> config) throws Exception {
        config
            .withConfiguration()
            .listener(listener());
    }

    /**
     * 创建状态机监听器
     */
    @Bean
    public StateMachineListener<PayStatus, PayEvent> listener() {
        return new StateMachineListenerAdapter<PayStatus, PayEvent>() {
            @Override
            public void transition(Transition<PayStatus, PayEvent> transition) {
                if (transition.getSource() != null && transition.getTarget() != null) {
                    System.out.println("支付状态转换: " + transition.getSource().getId() + " -> " + transition.getTarget().getId());
                } else if (transition.getSource() != null) {
                    System.out.println("支付状态转换: " + transition.getSource().getId() + " -> ");
                } else if (transition.getTarget() != null) {
                    System.out.println("支付状态转换:  -> " + transition.getTarget().getId());
                }
            }
        };
    }
}
