package package_four;

import java.util.ArrayList;
import java.util.List;

/**
 * 主线程类
 * @Author: xiaohuan
 * @Date: 2019-09-07 21:24
 */
public class MainThread {
  private List<SubThread> subThreadList = new ArrayList<SubThread>();

  /**
   * 创建子线程
   * @return
   */
  public synchronized SubThread createSubThread(){
    SubThread subThread = new SubThread();
    subThreadList.add(new SubThread());
    return subThread;
  }



  public boolean start(){
    CountLauncher countLauncher = new CountLauncher(subThreadList.size());
    for(SubThread subThread : subThreadList){
      subThread.setCountLauncher(countLauncher);
      new Thread(subThread).start();
    }

    while(countLauncher.getCount()>0){
      System.out.println(countLauncher.getCount());
    }

    /**
     * 判断子线程的执行结果
     */
    boolean result = true;
    for(SubThread subThread : subThreadList){
      if(subThread.getStatus()!=0){
        result = false;
        break;
      }
    }

    return result;
  }

  /**
   * 测试实例
   */
  public static void main(String[] args) {

    MainThread main = new MainThread();
    main.createSubThread();
    main.createSubThread();
    main.createSubThread();
    boolean result = main.start();
    System.out.println(result);
  }

}
