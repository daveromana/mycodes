package concurrentPro.huangxu;

public class MyRunnable implements Runnable {

	private int count;
	@Override
	public void run() {
		int index = ++count;
			System.out.println(index + " – run task …");
			try {
				if(!Thread.interrupted()){
					Thread.sleep(10);	
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(index + " – task end.");
	}

}
