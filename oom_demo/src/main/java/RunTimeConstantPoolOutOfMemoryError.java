import java.util.ArrayList;
import java.util.List;

/*
 * VM Args: -XX:PermSize=10M -XX:MaxPermSize=10M(限制常量池容量)
 *
 */
public class RunTimeConstantPoolOutOfMemoryError {

	public static void main(String[] args) {
		// 使用list保持常量池引用，避免常量池内的数据被垃圾回收清除
		List<String> list = new ArrayList<>();
		long i = 0;
		while (true) {
			String string = (i++) + "";
			list.add(string.intern());
		}
	}

}
