package package_three;

/**
 * 子线程类
 * @Author: xiaohuan
 * @Date: 2019-09-08 18:47
 */
public class SubThread implements Runnable{

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
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }
}
