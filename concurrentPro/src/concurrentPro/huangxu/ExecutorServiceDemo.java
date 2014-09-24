package concurrentPro.huangxu;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceDemo {

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(4);
		final int[] s = new int[]{0} ;
		executorService.submit(new Runnable() {
			@Override
			public void run() {
			int	sum = doSometing(1);
			s[0] =s[0]+sum ;
			System.out.println("s[0]+"+s[0]);
			}
		});
		executorService.submit(new Runnable() {
			@Override
			public void run() {
				int	sum =doSometing(2);
				s[0] =s[0]+sum ;
				System.out.println("s[0]+"+s[0]);
			}
		});
		executorService.submit(new Runnable() {
			@Override
			public void run() {
				int	sum = doSometing(3);
				s[0] =s[0]+sum ;
				System.out.println("s[0]+"+s[0]);
			}
		});
		executorService.submit(new Runnable() {
			@Override
			public void run() {
				int	sum = doSometing(4);
				s[0] =s[0]+sum ;
				System.out.println("s[0]+"+s[0]);
			}
		});
		executorService.shutdown();
		System.out.println("....main thread end !");
		doOtherSomting();
	}
	private static void doOtherSomting() {
		System.out.println("do other something");
		int sum = 0 ;
		for (int i = 0; i < 1000; i++) {
			sum +=i ;
		}
		System.out.println("sum:"+sum);
	}
	private static int doSometing(int id) {
		int sum = 0 ;
		System.out.println("task :"+id+" start do work");
		for (int i = 0; i < 10000; i++) {
			sum +=i ;
			
		}
		try {
			Thread.sleep(1000*2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 System.out.println("task :"+id+" finish  work");
		 return sum ;
	}
}
