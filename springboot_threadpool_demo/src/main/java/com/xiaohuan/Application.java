package com.xiaohuan;

import com.xiaohuan.multiple_thread.AsyncService;
import com.xiaohuan.multiple_thread_2.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xiaohuan
 * @Date: 2019-09-01 10:41
 */


@SpringBootApplication
public class Application implements CommandLineRunner {
  @Autowired
  private AsyncService asyncService;

  @Autowired
  private ThreadService threadService;

  public static void main(String[] args) throws InterruptedException {
    ConfigurableApplicationContext app = SpringApplication.run(Application.class, args);//.close();
    //Thread.currentThread().join();
    app.close();

  }

  @Override
  public void run(String... args) throws Exception {
    for (int i = 0; i < 10; i++) {
      asyncService.executeAsync();
    }
  }



  /*
  @Override
  public void run(String... args) throws Exception {
    List<String> idList = new ArrayList<>();
    idList.add("1");
    idList.add("10a");
    idList.add("1");
    idList.add("1");
    idList.add("2");
    idList.add("1");
    idList.add("3");
    idList.add("5");
    idList.add("5");
    idList.add("8");
    threadService.threadTest(idList);
  }
  */


}


/*
@SpringBootApplication
public class Application{
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);//.close();

    //SpringApplication app = new SpringApplication(Application.class);
    //app.setDefaultProperties(Collections.singletonMap("server.port", "8083"));
    //app.run(args).close();

  }
}
*/
