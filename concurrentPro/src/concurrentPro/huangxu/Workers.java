package concurrentPro.huangxu;

import java.util.Date;
import java.util.concurrent.CountDownLatch;

public class Workers extends Thread{
	String workerName;   
	int workTime;  
	CountDownLatch latch;
	public Workers(String workerName, int workTime, CountDownLatch latch) {
		super();
		this.workerName = workerName;
		this.workTime = workTime;
		this.latch = latch;
	} 
	@Override
	public void run() {
		System.out.println("worker :"+workerName+" do work begin at "+new Date());
		doWork();
		System.out.println("worker :"+workerName+" done work at "+new Date());
		latch.countDown();
	}
	private void doWork() {
		try {
			Thread.sleep(workTime);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
