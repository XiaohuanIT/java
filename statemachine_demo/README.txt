



#### guard
A guard can be used to validate some data before a transition to a state is executed. A guard looks very similar to an action:

```
@Bean
public Guard<String, String> simpleGuard() {
    return ctx -> (int) ctx.getExtendedState()
      .getVariables()
      .getOrDefault("approvalCount", 0) > 0;
}
```

The noticeable difference here is that a guard returns a true or false which will inform the state machine whether the transition should be allowed to occur.

Support for SPeL expressions as guards also exists. The example above could also have been written as:

```
.guardExpression("extendedState.variables.approvalCount > 0")
```


参考文档：
1、https://www.baeldung.com/spring-state-machine