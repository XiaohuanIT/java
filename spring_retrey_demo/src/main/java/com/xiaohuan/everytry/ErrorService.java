package com.xiaohuan.everytry;

import org.springframework.core.NestedRuntimeException;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

/**
 * 必须为Component或者Service等等，否则org.springframework.beans.factory.NoSuchBeanDefinitionException
 */
@Component
public class ErrorService {

	/**
	 * NestedRuntimeException是RemoteAccessException的父类，这样写也是可以的。可以成功调用到recover方法。
	 * 爷爷类RuntimeException也可以。
	 * 但是不能是别的异常，比如NullPointerException，会报错：org.springframework.retry.ExhaustedRetryException:
	 * Cannot locate recovery method; 方法名无所谓，不一定叫recover，叫lastMethod也可以。
	 * 如果直接没有@Recover这个，也是没问题的。默认throw了异常而已。
	 */
	// @Recover
	@Recover
	public void lastMethod(RuntimeException e) {
		System.out.println("recover running.");
		// System.out.println("recover:" + e.getMessage());
	}

	/**
	 * Recover可以多个，执行的是与子类最为匹配的。
	 */
	@Recover
	public void lastMethod(RemoteAccessException e) {
		System.out.println("recover3 running.");
	}

	/**
	 * Recover可以多个
	 */
	@Recover
	public void lastMethod(NestedRuntimeException e) {
		System.out.println("recover2 running.");
	}

	/**
	 * value=NestedRuntimeException也是可以的。
	 */
	@Retryable(value = {
			NestedRuntimeException.class }, maxAttempts = 2, backoff = @Backoff(delay = 2000L, multiplier = 1))
	public void call() throws NestedRuntimeException {// 这里throws RemoteAccessException也是可以的
		System.out.println("ErrorService do something...");
		throw new RemoteAccessException("RPC调用异常!!");
	}

	/**
	 * 如果RemoteService里调用的是call2，再调call，是不会重试。因为类内部方法调用不会重试。这是aop的坑。
	 */
	public void call2() throws NestedRuntimeException {
		call();
	}

}
