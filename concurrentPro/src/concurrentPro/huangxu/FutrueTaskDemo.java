package concurrentPro.huangxu;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutrueTaskDemo {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
		// 初始化一个Callable对象和FutureTask对象 
		Callable otherPerson = new OtherPerson();
		Callable otherPerson1 = new OtherPerson1();
		FutureTask futuretask = new FutureTask(otherPerson);
		FutureTask futuretask1 = new FutureTask(otherPerson1);
		//使用futuretask创建一个线程
		Thread newThread = new Thread(futuretask);
		Thread newThread1 = new Thread(futuretask1);
		System.out.println("newThread现在开始启动,启动时间为："+System.nanoTime());
		newThread.start();
		newThread1.start();
		System.out.println("东方不败在做其他事情!");
		System.out.println("东方不败在磨刀消毒");
		//兄弟线程在后台的计算线程是否完成，如果未完成则等待  
		//阻塞
//		while (!futuretask.isDone()) {
//			 try {
//				Thread.sleep(500);
//				 System.out.println("东方不败：“等兄弟回来了，我就和小弟弟告别……颤抖……”");  
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		System.out.println("newThread线程执行完毕，此时时间为" + System.nanoTime()); 
		String result = null; 
		String result1 = null;  
		try {
			result = (String) futuretask.get();
			result1 = (String) futuretask1.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		if("OtherPerson：：：经过一番厮杀取得《葵花宝典》".equals(result)){ 
			System.out.println("葵花宝典");  
			}
		if("OtherPerson1：：：经过一番厮杀取得《神功宝典》".equals(result1)){
			System.out.println("神功宝典");  
			}
		}
	}


