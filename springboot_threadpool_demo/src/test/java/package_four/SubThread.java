package package_four;

/**
 * 子线程类
 * @Author: xiaohuan
 * @Date: 2019-09-07 21:23
 */
public class SubThread implements Runnable{

  /**
   * 计数器类对象实例
   */
  private CountLauncher countLauncher;

  private int status = 99; //99-初始化  0-执行成功 1-执行失败


  public void run() {
    System.out.println("开始执行...");
    try{
      Thread.sleep(2000);
    }catch(Exception e){
      e.printStackTrace();
    }
    status=0;
    System.out.println("执行完毕...");
    countLauncher.countDown();
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public CountLauncher getCountLauncher() {
    return countLauncher;
  }

  public void setCountLauncher(CountLauncher countLauncher) {
    this.countLauncher = countLauncher;
  }

}
