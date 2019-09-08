package package_four;

/**
 * 计数器类
 * @Author: xiaohuan
 * @Date: 2019-09-07 21:22
 */
public class CountLauncher {
  private int count;

  public CountLauncher(int count){
    this.count = count;
  }

  public synchronized void countDown(){
    count --;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }
}
