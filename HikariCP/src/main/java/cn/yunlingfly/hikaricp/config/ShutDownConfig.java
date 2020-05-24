package cn.yunlingfly.hikaricp.config;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import javax.annotation.PreDestroy;

/**
 * @author huangqingshi
 * @Date 2019-08-17
 */
@Scope(value= ConfigurableBeanFactory.SCOPE_SINGLETON)
@Component
public class ShutDownConfig {

	@PreDestroy
	public void preDestroy() {
		System.out.println("关闭数据库连接等---------");
	}

}

