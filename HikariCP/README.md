网页访问http://localhost:8016/hello即可看到效果

更多有关于HikariCP配置参数含义戳->https://github.com/brettwooldridge/HikariCP#configuration-knobs-baby




spring boot优雅停止服务的方法：

1. actuator
curl -X POST http://localhost:8016/monitor/shutdown

2. context.close()

3. SpringApplication.exit(）
```java
int exitCode = SpringApplication.exit(context, (ExitCodeGenerator) () -> 0);
System.exit(exitCode);
```


对于mysql连接是否关闭，可以查看
```mysql
show processlist;
```



参考：
1. https://github.com/Yunlingfly/HikariCP.git
2. https://www.cnblogs.com/huangqingshi/p/11370291.html
