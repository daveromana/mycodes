package concurrentPro.huangxu;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolTest {

	public static void main(String[] args) {
		final AtomicInteger data = new AtomicInteger(100);
		//create three thread 
		ExecutorService threadPool = Executors.newScheduledThreadPool(3);
//		long time1 = System.currentTimeMillis();
//		for (int i = 0; i < 10; i++) {
//			final int task  = i ;
//			if(!threadPool.isShutdown()){
//				if(data.get()>0){threadPool.execute(new Runnable() {
//					//every task print one to ten 
//					@Override
//					public void run() {
//						for (int j = 0; j < 10; j++) {
//							data.decrementAndGet();
//							System.out.println(Thread.currentThread().getName()+" loop of "+data.get()+" for task "+task);
//						}
//					}
//				});}
//				
//			}
//		}
//		long time2 = System.currentTimeMillis();
//		long  time = time2 - time1 ;
//		System.out.println("total time "+time);
//		System.out.println("10 task has commited !");
//		threadPool.shutdown();//thread completed shutdown
		((ScheduledExecutorService) threadPool).schedule(new Runnable() {  
	        
	        @Override  
	        public void run() {  
	            System.out.println("bombing!");  
	        }  
	    }, 3, TimeUnit.SECONDS);  
	      
	}
	
	
}
