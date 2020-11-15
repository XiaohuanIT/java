import com.xiaohuan.User;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: xiaohuan
 * @Date: 2020/11/14 11:44
 */
public class Test1 {

	@Test
	public void test1() {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		User user = ac.getBean(User.class);
		System.out.println(user);
		ac.registerShutdownHook();

		ClassPathXmlApplicationContext ac1 = new ClassPathXmlApplicationContext("applicationContext.xml");
		User user1 = ac1.getBean(User.class);
		System.out.println("-------------"+ (ac==ac1) +"---------" + (user==user1));
		// 关闭销毁
		ac.registerShutdownHook();
	}
}
