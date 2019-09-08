package package_three;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xiaohuan
 * @Date: 2019-09-08 18:47
 */
public class MainThread {
  private List<SubThread> subThreadList = new ArrayList<SubThread>();

  /**
   * 创建子线程
   * @return
   */
  public SubThread createSubThread(){
    SubThread subThread = new SubThread();
    subThreadList.add(new SubThread());
    return subThread;
  }

  public boolean start(){
    for(SubThread subThread : subThreadList){
      new Thread(subThread).start();
    }

    /**
     * 监控所有子线程是否执行完毕
     */
    boolean continueFlag = true;
    while(continueFlag){
      for(SubThread subThread : subThreadList){
        if(subThread.getStatus()==99){
          continueFlag = true;
          break;
        }
        continueFlag = false;
      }
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


  public static void main(String[] args) {
    MainThread main = new MainThread();
    main.createSubThread();
    main.createSubThread();
    main.createSubThread();
    boolean result = main.start();
    System.out.println(result);
  }

}
