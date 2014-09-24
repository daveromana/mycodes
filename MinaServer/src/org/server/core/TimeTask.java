package org.server.core;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.session.IoSession;

public class TimeTask {
	
//	public static void getResponse(IoSession session,int m){
//		String message = "0000470x001101101992012092222400000201307071605";
//		String respData = MinaUtil.sendTCPMessage(message, "127.0.0.1", 9901, "UTF-8");
//		while(true) {
//		if(respData.equals("0x001")){
//			respData = MinaUtil.sendTCPMessage(message, "127.0.0.1", 9901, "UTF-8");
//			}else{
//				session.close(true);
//				session.getService().dispose();
//			}
//		try {
//			Thread.sleep(m);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		}
//	}
	
}
