package concurrentPro.huangxu;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class BlockingQueueTest {

	static long randomTime(){
		return (long) (Math.random()*1000);
	}
	public static void main(String[] args) {
		//能容纳100个文件的队列
		final BlockingQueue<File> queue = new LinkedBlockingQueue<File>();
		//线程池
		final ExecutorService executor = Executors.newFixedThreadPool(5);
		final File root = new File("E:\\disk\\blank");
		//完成标志
		final File exitFile = new File("");
		
		//读个数
		final AtomicInteger rc = new AtomicInteger();
		//写个数
		final AtomicInteger wc = new AtomicInteger();
		//读线程
		Runnable read = new Runnable() {
			
			@Override
			public void run() {
				scanFile(root);
				scanFile(exitFile);
			}

			private void scanFile(File root) {
				if(root.isDirectory()){
					File[] files = root.listFiles(new FileFilter() {
						
						@Override
						public boolean accept(File pathname) {
							return pathname.isDirectory()||pathname.getPath().endsWith(".log");
						}
					});
					for(File one:files){
					scanFile(one);	
					}
				}else{
					int index = rc.incrementAndGet();
					System.out.println("read0:"+index+" "+root.getPath());
					try {
						queue.put(root);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		executor.submit(read);
		//写线程
		for (int i = 0; i < 4; i++) {
			final int num = i;
			Runnable write = new Runnable() {
                String threadName = "Write" + num; 
				@Override
				public void run() {
					while (true) {
						try {
							Thread.sleep(randomTime());
							int index = wc.incrementAndGet();
							File file = queue.take();
							if(file==exitFile){
								queue.put(exitFile);
								break;
							}
							System.out.println(threadName+":"+index+" "+file.getPath());
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			};
			executor.submit(write);
		}
		executor.shutdown();
	}

}
