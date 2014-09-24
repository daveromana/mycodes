package concurrentPro.huangxu;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;

public abstract class Test {

	protected String id ;
	protected CyclicBarrier barrier ;
	protected long count ;
	protected int threadNum ;
	protected ExecutorService executor ;
	public Test(String id, CyclicBarrier barrier, long count, int threadNum,
			ExecutorService executor) {
		super();
		this.id = id;
		this.barrier = barrier;
		this.count = count;
		this.threadNum = threadNum;
		this.executor = executor;
	}
	public void startTest(){
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < threadNum; i++) {
			executor.execute(new Runnable() {
				
				@Override
				public void run() {
					for (int j = 0; j < count; j++) {
						test();
					}
					try {
						barrier.await();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});	
		}
		try {
			barrier.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
		// execute this step util all things completed
		long duration = System.currentTimeMillis()-startTime;
		System.out.println(id+" = "+duration);
	}
	protected abstract void test();
}
