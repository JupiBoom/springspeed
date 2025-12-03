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
 */
@Configuration
@EnableStateMachine(name = "paymentStateMachine")
public class PaymentStateMachineConfig extends EnumStateMachineConfigurerAdapter<PaymentStatus, PaymentEvent> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<PaymentStatus, PaymentEvent> config) throws Exception {
        config.withConfiguration()
                .autoStartup(true)
                .listener(listener());
    }

    @Override
    public void configure(StateMachineStateConfigurer<PaymentStatus, PaymentEvent> states) throws Exception {
        states
                .withStates()
                .initial(PaymentStatus.PENDING)
                .states(EnumSet.allOf(PaymentStatus.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<PaymentStatus, PaymentEvent> transitions) throws Exception {
        transitions
                // 待支付 -> 支付中
                .withExternal()
                .source(PaymentStatus.PENDING)
                .target(PaymentStatus.PROCESSING)
                .event(PaymentEvent.START_PAYMENT)
                .and()
                // 支付中 -> 支付成功
                .withExternal()
                .source(PaymentStatus.PROCESSING)
                .target(PaymentStatus.SUCCESS)
                .event(PaymentEvent.PAY_SUCCESS)
                .and()
                // 支付中 -> 支付失败
                .withExternal()
                .source(PaymentStatus.PROCESSING)
                .target(PaymentStatus.FAILED)
                .event(PaymentEvent.PAY_FAILED)
                .and()
                // 待支付 -> 已关闭
                .withExternal()
                .source(PaymentStatus.PENDING)
                .target(PaymentStatus.CLOSED)
                .event(PaymentEvent.CLOSE_PAYMENT)
                .and()
                // 支付中 -> 已关闭
                .withExternal()
                .source(PaymentStatus.PROCESSING)
                .target(PaymentStatus.CLOSED)
                .event(PaymentEvent.CLOSE_PAYMENT);
    }

    @Bean
    public StateMachineListener<PaymentStatus, PaymentEvent> listener() {
        return new StateMachineListenerAdapter<PaymentStatus, PaymentEvent>() {
            @Override
            public void transition(Transition<PaymentStatus, PaymentEvent> transition) {
                if (transition.getTarget().getId() == PaymentStatus.PENDING) {
                    System.out.println("状态机初始化成功");
                    return;
                }
                if (transition.getSource().getId() != null) {
                    System.out.println("支付状态变更: " + transition.getSource().getId() + " -> " + transition.getTarget().getId());
                }
            }
        };
    }
}
