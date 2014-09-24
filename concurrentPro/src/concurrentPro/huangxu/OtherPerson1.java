package concurrentPro.huangxu;

import java.util.concurrent.Callable;

public class OtherPerson1 implements Callable<Object> {

	@Override
	public Object call() throws Exception {
		//休息会儿
		Thread.sleep(5000*4);
		String result = "OtherPerson1：：：经过一番厮杀取得《神功宝典》";  
		System.out.println(result);
		return result ;
	}

}
