package com.xiaohuan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.StateMachine;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private StateMachine<States, Events> stateMachine;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        System.out.println("前面过程在创建容器，现在创建好了，先执行下列操作:");
        stateMachine.sendEvent(Events.E1);
        stateMachine.sendEvent(Events.E2);
        System.out.println(stateMachine.getState());
    }
}
