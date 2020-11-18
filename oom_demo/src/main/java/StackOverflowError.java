/*
 * 线程请求的栈深度超过虚拟机所允许的最大深度，将抛出StackOverflowError异常
 * 最常见引起此类异常的情形时使用不合理的递归调用
 * VM Args:-Xss256k
 *
 * @author Administrator
 *
 */
public class StackOverflowError {

	// 记录内存溢出时的栈深度
	private int stackLength = 1;

	// 递归调用的方法
	public void stackLeak() {
		stackLength++;
		stackLeak();
	}

	public static void main(String[] args) {

		StackOverflowError oomError = new StackOverflowError();
		try {
			oomError.stackLeak();
		} catch (Throwable e) {
			System.out.println("栈深度为：" + oomError.stackLength);
			throw e;
		}
	}

}
