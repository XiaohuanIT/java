package com.xiaohuan.action;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Component
public class InitAction implements Action<String, String> {
    @Override
    public void execute(StateContext<String, String> context) {
        System.out.println(context.getTarget().getId());
    }
}
