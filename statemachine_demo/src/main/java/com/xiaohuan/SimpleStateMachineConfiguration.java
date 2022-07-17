package com.xiaohuan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.DefaultStateMachineContext;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;

/**
 * 订单状态机配置
 */
@Configuration
@EnableStateMachine(name = "stateMachine")
public class SimpleStateMachineConfiguration extends StateMachineConfigurerAdapter<States, Events> {

    @Autowired
    private StateMachineListener stateMachineListener;

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config)
            throws Exception {
        config
                .withConfiguration()
                .autoStartup(true)
                .listener(stateMachineListener);
    }

    /**
     * 配置状态
     * @param states
     * @throws Exception
     */
    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states)
            throws Exception {
        states
                .withStates()
                .initial(States.SI)
                .states(EnumSet.allOf(States.class));
    }

    /**
     * 配置状态转换事件关系
     * @param transitions
     * @throws Exception
     */
    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions)
            throws Exception {
        transitions
                .withExternal()
                .source(States.SI).target(States.S1).event(Events.E1)
                .and()
                .withExternal()
                .source(States.S1).target(States.S2).event(Events.E2);
    }











    /**
     * 持久化配置
     * 实际使用中，可以配合redis等，进行持久化操作
     * @return
     */
    /*
    @Bean
    public StateMachinePersister<String, String, Order> persister(){
        return new DefaultStateMachinePersister<>(new StateMachinePersist<String, String, Order>() {
            @Override
            public void write(StateMachineContext<String, String> context, Order order) throws Exception {
                //此处并没有进行持久化操作
            }

            @Override
            public StateMachineContext<String, String> read(Order order) throws Exception {
                //此处直接获取order中的状态，其实并没有进行持久化读取操作
                return new DefaultStateMachineContext<>(order.getStatus(), null, null, null);
            }
        });
    }

     */

}
