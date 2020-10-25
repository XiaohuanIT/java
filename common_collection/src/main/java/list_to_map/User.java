package list_to_map;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: xiaohuan
 * @Date: 2020/10/25 19:57
 */
// 简单对象
@Accessors(chain = true) // 链式方法
@Data
class User {
	private String id;
	private String name;
}
