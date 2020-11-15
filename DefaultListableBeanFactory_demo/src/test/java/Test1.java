import com.xiaohuan.User;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * @Author: xiaohuan
 * @Date: 2020/11/15 05:03
 */

public class Test1 {
	public static void main(String[] args) {
		Resource res = new ClassPathResource("beans.xml");

		DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
		reader.loadBeanDefinitions(res);

		User chenjie = factory.getBean("chenjie", User.class);
		System.out.println(chenjie.getUsername());

		User chenjie2 = factory.getBean("chenjie", User.class);
		System.out.println(chenjie2.getDog().getName());
	}
}
