package concurrentPro.huangxu;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorTest {
	public static void main(String[] args) {
		ScheduledThreadPoolExecutor  excutor = new ScheduledThreadPoolExecutor(1);
		excutor.scheduleWithFixedDelay(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("重复执行");
				
			}
		}, 1, 2, TimeUnit.SECONDS);
	}
}
