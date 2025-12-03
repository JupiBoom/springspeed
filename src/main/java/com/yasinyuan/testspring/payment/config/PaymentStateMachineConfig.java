package com.yasinyuan.testspring.payment.config;

import com.yasinyuan.testspring.payment.enums.PaymentEvent;
import com.yasinyuan.testspring.payment.enums.PaymentStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.transition.Transition;

import java.util.EnumSet;

/**
 * 支付状态机配置
 * @author yinyuan
 */
@Configuration
@EnableStateMachine
public class PaymentStateMachineConfig extends EnumStateMachineConfigurerAdapter<PaymentStatus, PaymentEvent> {

    /**
     * 配置状态
     */
    @Override
    public void configure(StateMachineStateConfigurer<PaymentStatus, PaymentEvent> states) throws Exception {
        states
            .withStates()
            .initial(PaymentStatus.PENDING)
            .states(EnumSet.allOf(PaymentStatus.class));
    }

    /**
     * 配置状态转换
     */
    @Override
    public void configure(StateMachineTransitionConfigurer<PaymentStatus, PaymentEvent> transitions) throws Exception {
        transitions
            // 待支付 -> 支付中
            .withExternal()
            .source(PaymentStatus.PENDING)
            .target(PaymentStatus.PROCESSING)
            .event(PaymentEvent.INITIATE_PAYMENT)
            .and()
            // 支付中 -> 支付成功
            .withExternal()
            .source(PaymentStatus.PROCESSING)
            .target(PaymentStatus.SUCCESS)
            .event(PaymentEvent.PAYMENT_SUCCESS)
            .and()
            // 支付中 -> 支付失败
            .withExternal()
            .source(PaymentStatus.PROCESSING)
            .target(PaymentStatus.FAILED)
            .event(PaymentEvent.PAYMENT_FAILED)
            .and()
            // 待支付 -> 已关闭（超时）
            .withExternal()
            .source(PaymentStatus.PENDING)
            .target(PaymentStatus.CLOSED)
            .event(PaymentEvent.PAYMENT_TIMEOUT)
            .and()
            // 待支付 -> 已关闭（主动关闭）
            .withExternal()
            .source(PaymentStatus.PENDING)
            .target(PaymentStatus.CLOSED)
            .event(PaymentEvent.CLOSE_PAYMENT);
    }

    /**
     * 配置状态机监听器
     */
    @Override
    public void configure(StateMachineConfigurationConfigurer<PaymentStatus, PaymentEvent> config) throws Exception {
        config
            .withConfiguration()
            .listener(listener());
    }

    @Bean
    public StateMachineListener<PaymentStatus, PaymentEvent> listener() {
        return new StateMachineListenerAdapter<PaymentStatus, PaymentEvent>() {
            @Override
            public void transition(Transition<PaymentStatus, PaymentEvent> transition) {
                if (transition.getTarget().getId() != null) {
                    System.out.println("状态转换: " + transition.getSource().getId() + " -> " + transition.getTarget().getId() + ", 事件: " + transition.getTrigger().getEvent());
                }
            }
        };
    }
}
