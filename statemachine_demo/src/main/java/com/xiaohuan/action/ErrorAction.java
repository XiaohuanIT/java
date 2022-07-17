package com.xiaohuan.action;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Component
public class ErrorAction  implements Action<String, String> {
    @Override
    public void execute(StateContext<String, String> context) {
        System.out.println(
                "Error " + context.getSource().getId() + context.getException());
    }
}
