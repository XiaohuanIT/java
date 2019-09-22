package package_five;

/**
 * @Author: xiaohuan
 * @Date: 2019-09-10 22:09
 */
public   class  JoinThread1  extends  Thread
{
  public   static int  n  =   0 ;

  public static   synchronized   void  inc()
  {
    n ++ ;
  }
  public   void  run()
  {
    for  ( int  i  =   0 ; i  <   10 ; i ++ )
      try
      {
        inc();  //  n = n + 1 改成了 inc();
        sleep( 3 );  //  为了使运行结果更随机，延迟3毫秒

      }
      catch  (Exception e)
      {
      }
  }

  public   static   void  main(String[] args)  throws  Exception
  {
    Thread threads[]  =   new  Thread[ 100 ];
    for  ( int  i  =   0 ; i  <  threads.length; i ++ )
      //  建立100个线程
      threads[i]  =   new  JoinThread1();
    for  ( int  i  =   0 ; i  <  threads.length; i ++ )
      //  运行刚才建立的100个线程
      threads[i].start();
    for  ( int  i  =   0 ; i  <  threads.length; i ++ )
      //  100个线程都执行完后继续
      threads[i].join();
    System.out.println( " n= "   +  JoinThread1.n);
  }
}
