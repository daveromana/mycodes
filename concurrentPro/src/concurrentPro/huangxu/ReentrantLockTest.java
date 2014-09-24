package concurrentPro.huangxu;

import java.awt.image.SampleModel;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {

	private static long COUNT = 1000000 ;
	private static Lock lock = new ReentrantLock();
	private static long lockCounter = 0 ;
	private static long synCounter = 0 ;
	private static long semapCounter = 0 ;
	private static AtomicInteger atomicCounter = new AtomicInteger(0);
	private static Object synLock = new Object();
	private static Semaphore mutex = new Semaphore(1);
	
	public static void testLock(int num,int threadCount){
		
	}
	/** Lock test**/
	static long getLock(){
		lock.lock();
		try {
			return lockCounter;
		} finally {
			lock.unlock();
		}
	}
	/** synchronized test**/
	static long getSyn(){
		synchronized (synLock) {
			return synCounter ;
		}
	}
	
	/** AtomicInteger test**/
	static long getAtomic(){
		return atomicCounter.get();
	}
	
	/** Semaphone test**/
	static long getSemaphone() throws InterruptedException{
		mutex.acquire();
		try {
		return 	semapCounter;
		} finally{
			mutex.release();
		}
	}
	
	static long getLockInc(){
		lock.lock();
		try {
		return ++lockCounter ;	
		} finally {
			lock.unlock();
		}
		
	}
	static long getSyncInc(){
		synchronized (synLock) {
			return ++synCounter ;
		}
	}
	static long getAtomicInc(){
		return atomicCounter.getAndIncrement();
	}
	static class SemaTest extends Test{

		public SemaTest(String id, CyclicBarrier barrier, long count,
				int threadNum, ExecutorService executor) {
			super(id, barrier, count, threadNum, executor);
		}

		@Override
		protected void test() {
			try {
				getSemaphone();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	static class LockTest extends Test{
		public LockTest(String id, CyclicBarrier barrier, long count,
					int threadNum, ExecutorService executor) {
				super(id, barrier, count, threadNum, executor);
		}
			@Override
			protected void test() {
				getLock();
			}	
		}
	static class SyncTest extends Test{

		public SyncTest(String id, CyclicBarrier barrier, long count,
				int threadNum, ExecutorService executor) {
			super(id, barrier, count, threadNum, executor);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void test() {
			getSyn();
		}
		
	}
	static class AtomicTest extends Test{

		public AtomicTest(String id, CyclicBarrier barrier, long count,
				int threadNum, ExecutorService executor) {
			super(id, barrier, count, threadNum, executor);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void test() {
			getAtomic();
			
		}
		
	}
	public static void test(String id,long count,int threadNum,ExecutorService executor){
		final CyclicBarrier barrier = new CyclicBarrier(threadNum+1, new Thread(){
			@Override
			public void run() {
				super.run();
			}
		});
		System.out.println("==============================");  
		System.out.println("count = "+count+" "+"threadNum = "+threadNum);  
		new LockTest("Lock", barrier, COUNT, threadNum, executor).startTest();
		new SyncTest("Sync", barrier, COUNT, threadNum, executor).startTest();
		new AtomicTest("Atomic", barrier, COUNT, threadNum, executor).startTest();
		new SemaTest("Semaphone", barrier, COUNT, threadNum, executor).startTest();
		System.out.println("==============================");
	}
	public static void main(String[] args) {
		for (int i = 1; i < 5; i++) {
		ExecutorService executor = Executors.newFixedThreadPool(10*i);
		test("", COUNT*i, 10*i, executor);
		System.out.println(getSyncInc());
		}
	}
}


