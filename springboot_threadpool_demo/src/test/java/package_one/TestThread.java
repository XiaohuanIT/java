package package_one;

/**
 * @Author: xiaohuan
 * @Date: 2019-09-01 22:32
 */
public class TestThread extends Thread
{
  public void run()
  {
    System.out.println(this.getName() + "子线程开始");
    try
    {
      // 子线程休眠五秒
      Thread.sleep(5000);
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }
    System.out.println(this.getName() + "子线程结束");
  }
}
