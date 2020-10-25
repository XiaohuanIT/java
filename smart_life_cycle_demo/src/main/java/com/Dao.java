import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Dao {
	private static Map<String, String> serviceDataMap = new HashMap<>(10);

	private Dao(){
		System.out.println("构造方法!");
	}

	static {
		System.out.println("component static code.");
	}

}
