import org.junit.Test;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.ResolvableType;

import static org.junit.Assert.assertEquals;

/**
 * @Author: xiaohuan
 * @Date: 2020/11/15 18:44
 */
public class Test2 {

	@Test
	public void testGenericsBasedInjectionWithBeanDefinitionTargetResolvableType() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		RootBeanDefinition bd = new RootBeanDefinition();
		bd.setInstanceSupplier(TypedFactoryBean::new);
		bd.setTargetType(ResolvableType.forClassWithGenerics(FactoryBean.class, String.class));
		bd.setLazyInit(true);
		context.registerBeanDefinition("fb", bd);
		context.refresh();

		// 获取bean
		assertEquals(String.class, context.getType("fb"));
		// 获取factory的工厂类
		assertEquals(FactoryBean.class, context.getType("&fb"));
	}

	static class TypedFactoryBean implements FactoryBean<String> {

		public TypedFactoryBean() {
			throw new IllegalStateException();
		}

		@Override
		public String getObject() {
			return "";
		}

		@Override
		public Class<?> getObjectType() {
			return String.class;
		}

		@Override
		public boolean isSingleton() {
			return true;
		}
	}
}
