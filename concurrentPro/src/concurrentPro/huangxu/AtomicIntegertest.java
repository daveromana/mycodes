package concurrentPro.huangxu;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegertest {

	static AtomicInteger data = new AtomicInteger(1);
	public static void main(String[] args) {
		System.out.println("before:"+data.get());
		new Thread(new  Runnable() {
			public void run() {
				data.incrementAndGet();
				System.out.println("incrementAndGet:"+data.get());
			}
		}).start();
		
		new Thread(new  Runnable() {
			public void run() {
				data.decrementAndGet();
				System.out.println("decrementAndGet:"+data.get());
			}
		}).start();
		System.out.println("after:"+data.get());
	}
}
