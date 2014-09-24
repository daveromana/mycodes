package org.huangxu.service;

import java.sql.Connection;

import org.huangxu.factory.BoneCPFactory;
import org.huangxu.handler.Handler;
import org.huangxu.handler.HandlerManager;
import org.huangxu.handler.LoginHandler;
import org.huangxu.handler.RegistHandler;
import org.huangxu.handler.XinTiaoHandler;
import org.huangxu.message.Message;
import org.huangxu.opcode.OpCode;
import org.huangxu.service.Service;


public class DbService implements Service {

	@Override
	public String getServiceName() {
		return "dbService";
	}

	@Override
	public void processDown(Message paramMessage) {
		Message msg= paramMessage ;
		try {
			Handler abstracthandler = HandlerManager.Singleton.Handlers[msg.getMsgType()];
		
		if(abstracthandler!=null){
			abstracthandler.execute(msg.getSession(), msg);
		}else{
			msg.getSession().write(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isRunning() {
		return false;
	}

	@Override
	public boolean haveFailed() {
		return false;
	}

	@Override
	public boolean init() {
		String driverName = Configuration.getInstance().getDriverName();
		String url = Configuration.getInstance().getJdbcUrl();
		String username = Configuration.getInstance().getUserName();
		String password = Configuration.getInstance().getPassword();
		int minConnections = Configuration.getInstance().getMinConnections() ;
		int maxConnections = Configuration.getInstance().getMaxConnections() ;
		boolean flag = BoneCPFactory.getInstance().initDB
				(driverName,
				url, username, password, minConnections, maxConnections);
		if(flag){
			HandlerManager.Singleton.setupHandler(100000);
			HandlerManager.Singleton.Handlers[OpCode.C_LOGIN]= new LoginHandler();
			HandlerManager.Singleton.Handlers[OpCode.C_REGIST]= new RegistHandler();
			HandlerManager.Singleton.Handlers[OpCode.C_XINTIAO]= new XinTiaoHandler();
		}
		return flag;
	}

	@Override
	public void end() {

	}

}
