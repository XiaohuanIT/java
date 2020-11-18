import java.util.ArrayList;
import java.util.List;

/**
 * JVM堆中存储java对象实例和数组
 * VM Args: -Xms20m -Xmx20m (限制堆的大小不可扩展)
 * @author Administrator
 *
 */
public class HeapOutOfMemoryError_2 {

	public static class OOMObject {
	}
	public static void main(String[] args) {
		while (true) {
			List<Object> list=new ArrayList<>();
			list.add(new OOMObject());
			System.out.println(System.currentTimeMillis());
		}
	}

}
