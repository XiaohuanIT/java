package com.xiaohuan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import com.xiaohuan.everytry.AbstractService;
import com.xiaohuan.everytry.ChildService;
import com.xiaohuan.everytry.ErrorService;

@Service
public class RemoteService {

	/**
	 * 16:51:35.535 [main] DEBUG org.springframework.retry.support.RetryTemplate -
	 * Retry: count=0
	 *
	 * 16:51:35.549 [main] DEBUG
	 * org.springframework.retry.backoff.ExponentialBackOffPolicy - Sleeping for
	 * 2000
	 *
	 * 16:51:37.554 [main] DEBUG org.springframework.retry.support.RetryTemplate -
	 * Checking for rethrow: count=1
	 *
	 * 日志中的重试打印均为debug级别。
	 */
	@Retryable(value = {
			RemoteAccessException.class }, maxAttempts = 3, backoff = @Backoff(delay = 5000L, multiplier = 1))
	public void call() throws Exception {
		System.out.println("do something...");
		throw new RemoteAccessException("RPC调用异常");
	}

	@Recover
	public void recover(RemoteAccessException e) {
		System.out.println(e.getMessage());
	}

	/**
	 * 如果这样子注入了，就可以重试了。
	 */
	@Autowired
	ErrorService notInjectService;

	public void callByNewObject() throws Exception {
		System.out.println("如果通过new对象来调用，而不是注入，是不会重试的。");
		// notInjectService = new NotInjectService();// 这样子不会触发重试，因为不是注入
		notInjectService.call();
	}

	/**
	 * 不能调用这个的call方法。因为print没实现嘛。
	 */
	@Autowired
	AbstractService abstractService;

	/**
	 * 与上面连在一起没有autowired会报错：java.lang.NullPointerException
	 */
	@Autowired
	ChildService childService;

	public void callAbstractObject() throws Exception {
		System.out.println("抽象类的调用。");
		childService.call();
	}
}
