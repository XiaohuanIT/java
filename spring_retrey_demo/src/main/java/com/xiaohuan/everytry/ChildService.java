package com.xiaohuan.everytry;

import org.springframework.stereotype.Service;

/**
 * 抽象类有@service不管用，因为注入的是ChildService，因此没有@service会报错：NoSuchBeanDefinitionException:
 * No qualifying bean of type 'com.danni.everytry.AbstractService' available:
 * expected at least 1 bean which qualifies as autowire candidate. Dependency
 * annotations:
 * {@org.springframework.beans.factory.annotation.Autowired(required=true)}
 */
@Service
public class ChildService extends AbstractService {

	@Override
	void print() {
		System.out.println("ChildService print.");
	}

	/**
	 * 如果把父类的call复写了，就不会重试了。除非里面也调用super.call()
	 */
	@Override
	public void call() throws Exception {
		System.out.println("ChildService call start.");

		// 如果不调用调用super.call()，不会重试，除非这个方法也@Retryalbe
		super.call();

		// 这一句是不会打印的，因为上面call里抛了异常
		System.out.println("ChildService call finished.");
	}
}
