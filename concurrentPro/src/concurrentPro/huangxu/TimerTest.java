package concurrentPro.huangxu;

import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {

	public static void main(String[] args) {
		Timer time = new Timer();
//		time.schedule(new MyTask() , 1000);
		long firstTime = System.currentTimeMillis();
		time.scheduleAtFixedRate(new MyTask(), 1000 , 1000);
	}
	
}
class MyTask extends TimerTask{
	@Override
	public void run() {
		System.out.println("执行定时器了");
		
	}	
}


