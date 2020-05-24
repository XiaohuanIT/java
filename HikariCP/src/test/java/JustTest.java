/**
 * @Author: xiaohuan
 * @Date: 2020/5/22 11:20
 */
public class JustTest {
	private static final int COUNT_BITS = Integer.SIZE - 3;
	//线程池的最大容量,其值的二进制为:00011111111111111111111111111111（29个1）
	private static final int CAPACITY   = (1 << COUNT_BITS) - 1;

	// 线程池的运行状态，总共有5个状态，用高3位来表示
	private static final int RUNNING    = -1 << COUNT_BITS;
	private static final int SHUTDOWN   =  0 << COUNT_BITS;
	private static final int STOP       =  1 << COUNT_BITS;
	private static final int TIDYING    =  2 << COUNT_BITS;
	private static final int TERMINATED =  3 << COUNT_BITS;

	public static void main(String[] args){
		System.out.println(Integer.toBinaryString(-1));
		System.out.println(Integer.toBinaryString(RUNNING));
		System.out.println(Integer.toBinaryString(SHUTDOWN));
		System.out.println(Integer.toBinaryString(STOP));
		System.out.println(Integer.toBinaryString(TIDYING));
		System.out.println(Integer.toBinaryString(TERMINATED));
	}
}
