package com.xiaohuan;

import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.stereotype.Component;

/**
 * 注意这里的bean，配置在SimpleStateMachineConfiguration中的`.listener`中生效
 */
@Component("stateListener")
@WithStateMachine(name = "stateMachine")
public class StateMachineListener extends StateMachineListenerAdapter<States, Events> {

    @Override
    public void stateChanged(State<States, Events> from, State<States, Events> to) {
        System.out.printf("Transitioned from %s to %s%n", from == null ? "none" : from.getId(), to.getId());
    }
}
