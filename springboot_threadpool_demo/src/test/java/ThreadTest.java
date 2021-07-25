import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * 阻塞式IO(BIO)操作(网络阻塞)的时候，Java的线程状态是Blocked or Waiting?
 */
public class ThreadTest {
    @Test
    public void testInBlockedIOState() throws InterruptedException {
        Scanner in = new Scanner(System.in);
        // 创建一个名为“输入输出”的线程t
        Thread t = new Thread(new Runnable() {
            @Override
            public
            void run() {
                try {
                    // 命令行中的阻塞读
                    String input = in.nextLine();
                    System.out.println(input);
                }
                catch (Exception e) {
                    e.printStackTrace();
                } finally{
                    IOUtils.closeQuietly(in);
                }
            }
        }, "输入输出");
        // 线程的名字
        // 启动
        t.start();
        // 确保run已经得到执行
        Thread.sleep(100);
        // 状态为RUNNABLE
        Assert.assertTrue((t.getState()).equals(Thread.State.RUNNABLE));

    }


    @Test
    public void testBlockedSocketState() throws Exception {
        Thread serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                ServerSocket serverSocket = null;
                try {
                    serverSocket = new ServerSocket(10086);
                    while (true) {
                        // 阻塞的accept方法
                        Socket socket = serverSocket.accept();
                        // TODO
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    try {
                        serverSocket.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            }, "socket线程");

        // 线程的名字
        serverThread.start();
        // 确保run已经得到执行
        Thread.sleep(500);
        // 状态为RUNNABLE
        Assert.assertTrue(serverThread.getState().equals(Thread.State.RUNNABLE));
    }
}
