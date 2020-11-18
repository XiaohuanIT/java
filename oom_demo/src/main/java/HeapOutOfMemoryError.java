import java.util.ArrayList;
import java.util.List;

/**
 * JVM堆中存储java对象实例和数组
 * VM Args: -Xms20m -Xmx20m (限制堆的大小不可扩展)
 * @author Administrator
 *
 */
public class HeapOutOfMemoryError {

	public static class OOMObject {
	}
	public static void main(String[] args) {
		List<Object> list=new ArrayList<>();
		// 不断创建对象，并保证GC Roots到对象之间有可达路径，避免垃圾回收清除创建的对象
		while (true) {
			list.add(new OOMObject());
			System.out.println(System.currentTimeMillis());
		}
	}

}
