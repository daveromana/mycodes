package test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.huangxu.message.Message;
public class MyTest {

	public static void main(String[] args) throws Exception {
	ExecutorService executorService = Executors.newFixedThreadPool(2000);
	for (int i = 0; i < 2000; i++) {
		executorService.submit(new Runnable() {
			
			@Override
			public void run() {
				UserUtil.login("hx", "1");
				
			}
		});
		Thread.sleep(10);
	}
	executorService.shutdown();
	}
}
