package list_to_map;

import com.google.common.collect.Lists;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: xiaohuan
 * @Date: 2020/10/25 19:58
 */
public class ConvertList {
	static List<User> userList1 = Lists.newArrayList(
			new User().setId("A").setName("张三"),
			new User().setId("B").setName("李四"),
			new User().setId("C").setName("王五")
	);

	static List<User> userList2 = Lists.newArrayList(
			new User().setId("A").setName("张三"),
			new User().setId("A").setName("李四"),
			new User().setId("C").setName("王五")
	);

	public static void main(String[] args){
		Map<String, String> map = new HashMap<>();
		for (User user : userList1) {
			map.put(user.getId(), user.getName());
		}

		java8Function();
		java8Function2();
	}

	public static void java8Function(){
		Map<String, String> map = userList1.stream().collect(Collectors.toMap(User::getId, User::getName));

		// 如果希望得到 Map 的 value 为对象本身时，可以这样写：
		Map<String, User> map1 = userList1.stream().collect(Collectors.toMap(User::getId, t -> t));

		Map<String, User> map2 = userList1.stream().collect(Collectors.toMap(User::getId, Function.identity()));
	}


	public static void java8Function2(){
		//Map<String, String> map1 = userList2.stream().collect(Collectors.toMap(User::getId, User::getName));
		Map<String, String> map2 = userList2.stream().collect(Collectors.toMap(User::getId, User::getName, (n1, n2) -> n1 + n2));
	}
}
