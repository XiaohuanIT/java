package com.demo1;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

/**
 * @Author: xiaohuan
 * @Date: 2019-08-17 21:55
 */
public class Client {
  public static void main(String[] args) {
    //将代理类存到本地磁盘
    System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/Users/yangxiaohuan/Downloads/java_tmp");
    //使用enhancer，设置相关参数
    //实例化增强器
    Enhancer enhancer = new Enhancer();
    //设置需要代理的目标类
    enhancer.setSuperclass(Dao.class);
    //设置拦截对象 回调的实现类
    // enhancer.setCallback(new MethodInterceptorImpl());

    // 回调实例数组
    Callback[] callbacks = new Callback[]{new MethodInterceptorImpl(), new DaoAnotherProxy(), NoOp.INSTANCE};
    enhancer.setCallbacks(callbacks);
    enhancer.setCallbackFilter(new DaoFilter());
    //使用create 返回Object 生成代理类并返回实例
    //产生代理对象
    Dao dao = (Dao) enhancer.create();
    //select优先级高 使用DaoProxy
    dao.select();
    //无法代理被final修饰的方法
    dao.delete();
    dao.insert();
  }
}
