package concurrentPro.huangxu;

import java.util.Date;
import java.util.concurrent.CountDownLatch;

import javax.swing.text.DateFormatter;

public class CountDownLatchDemo {

	public static void main(String[] args) {
		CountDownLatch latch = new CountDownLatch(2);//2个计数器
		 Workers worker1 = new Workers("huangxu",8000,latch);
		 Workers worker2 = new Workers("xuting",5000,latch);
		 worker1.start(); 
		 worker2.start();
		 try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		 System.out.println("all workers done at "+new Date());
	}
	
}
