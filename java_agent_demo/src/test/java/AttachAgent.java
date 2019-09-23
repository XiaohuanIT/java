import com.sun.tools.attach.VirtualMachine;

import java.util.Scanner;

/**
 * @Author: xiaohuan
 * @Date: 2019-09-22 15:53
 */
public class AttachAgent {
  public static void main(String[] args) throws Exception{
    // 这里是 需要 热部署的 java进程的id，运行 jps命令即可知道。
    VirtualMachine vm = VirtualMachine.attach("10063");//注意，换乘PersonTest的进程id
    vm.loadAgent("/Users/yangxiaohuan/my_private/github/my_public/java/java_agent_demo/build/libs/java_agent_demo-1.0-SNAPSHOT.jar");
    /*
    while (true) {
      Scanner reader = new Scanner(System.in);
    }
    */
  }
}
