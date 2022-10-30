package cn.enjoyedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class RocketWithSpringbootApplication {

	public static void main(String[] args) {
		//默认情况下，启动Tomcat(8080)
		SpringApplication.run(RocketWithSpringbootApplication.class, args);
	}
}
