package concurrentPro.huangxu;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class DeployDemo {

	private int count;
	private ScheduledExecutorService executorService;
	ScheduledFuture scheduledFuture ;
	ScheduledFuture scheduledFuture2 ;

	public DeployDemo() {
		MyRunnable runner = new MyRunnable();
		this.executorService = Executors.newScheduledThreadPool(5);
		this.scheduledFuture = this.executorService.scheduleWithFixedDelay(runner,1000, 1000, TimeUnit.MILLISECONDS);
		this.scheduledFuture2 = this.executorService.scheduleWithFixedDelay(runner,1000, 1000, TimeUnit.MILLISECONDS);
		executorService.schedule(new Runnable() {
			
			@Override
			public void run() {
				scheduledFuture.cancel(true);
				scheduledFuture2.cancel(true);
				executorService.shutdown();
				
			}
		}, 10, TimeUnit.SECONDS);
	}

	public static void main(String[] args) {
		DeployDemo demo =new DeployDemo();
//		demo.scheduledFuture.cancel(false);
	}

}
