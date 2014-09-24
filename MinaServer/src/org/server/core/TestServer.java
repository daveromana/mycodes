package org.server.core;

public class TestServer {
	public static void main(String[] args) {
		String message = "00004710005101101992012092222400000201307071605";
		Object respData = MinaUtil.sendTCPMessage(message, "127.0.0.1", 9901, "UTF-8");
		System.out.println("respData:"+respData);
//		TimeTask.getResponse(session,1000);
		
	}

//	@T
//	public void testTcp(){
//		String message = "00004710005101101992012092222400000201307071605";
//		String respData = MinaUtil.sendTCPMessage(message, "127.0.0.1", 9901, "UTF-8");
//		Assert.assertEquals("00003099999999`20130707144028`", respData);
//	}

}
