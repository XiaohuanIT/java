import com.xiaohuan.EmailEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: xiaohuan
 * @Date: 2020/11/15 04:35
 */
public class Test1 {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

		//HelloBean hello = (HelloBean) context.getBean("helloBean");
		//hello.setApplicationContext(context);
		EmailEvent event = new EmailEvent("hello","boylmx@163.com","this is a email text!");
		context.publishEvent(event);
		//System.out.println();
	}
}
