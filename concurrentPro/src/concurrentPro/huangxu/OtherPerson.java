package concurrentPro.huangxu;

import java.util.concurrent.Callable;

@SuppressWarnings("rawtypes")
public class OtherPerson implements Callable {

	@Override
	public Object call() throws Exception {
		//休息会儿
		Thread.sleep(50000);
		String result = "OtherPerson：：：经过一番厮杀取得《葵花宝典》";  
		System.out.println(result);
		return result;
	}

}
