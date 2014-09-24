package org.huangxu.service;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Server {

	Logger logger = LoggerFactory.getLogger(Server.class);
	protected Service service;
	private static final Server instance = new Server();
	public static Server getInstance(){
		return instance ;
	}
	private boolean startService() {
		if (this.service == null) {
			logger.warn("There aren't services defined for this server!");
			return false;
		}
		try {
			if ((!this.service.init()) &&(!this.service.isRunning())) {
				logger.error("Could not start services. Status is: "
						+ this.service.isRunning());
				if (this.service.isRunning())
					this.service.end();
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
			logger.error(
					"Could not start services. Status is: "
							+ this.service.isRunning(), e);
			return false;
		}

		return true;
	}

	private void stopService() {
		if ((this.service != null) && (this.service.isRunning()))
			this.service.end();
	}

	public Service getService() {
		return this.service;
	}

	private boolean instanceServices() {
		String serviceName = Configuration.getInstance().getServiceName();
		if (serviceName == null) {
			logger.error("No services are defined for this server");
			return false;
		}
		try {
			Object o = Class.forName(serviceName).newInstance();
			if ((o instanceof Service)) {
				this.service = ((Service) o);
			} else {
				logger.error("Class "
						+ this.service
						+ " is not a proper service (it does not implement 'ServiceInterface'");
				return false;
			}
		} catch (Exception e) {
			logger.warn("Can't instantiate " + this.service + ": "
					+ e.getClass().getCanonicalName());
			if (logger.isDebugEnabled()) {
				logger.debug("Can't instantiate " + this.service, e);
			}

		}

		return true;
	}
	
	public void init(){
		if(!instanceServices()){
			logger.error("the service instance failed !");
			return ;
		}
		if(!startService()){
			logger.error("Can't start service !");
			return ;
		}
		boolean flag = availablePort(Integer.parseInt(Configuration.getInstance().getPort()));
		if(flag){
			logger.error("the port is not available !");
			return ;
		}
		startServer();
	}

	public boolean availablePort(int port){
		boolean flag = false;  
		try { 
		InetAddress theAddress = InetAddress.getByName("127.0.0.1");  
		Socket socket = new Socket(theAddress,port);  
		flag = true;  
	    } catch (IOException e) {  
	          
		 }  
		    return flag;

	}
	private void startServer() {
		int port = Integer.parseInt(Configuration.getInstance().getPort());
		int idleTime = Integer.parseInt(Configuration.getInstance().getIdleTime());
		int readBufferSize = Integer.parseInt(Configuration.getInstance().getReadBufferSize());
		IoAcceptor  acceptor = null ; //create connect 
		try {
			acceptor = new NioSocketAcceptor();//创建一个非阻塞的Socket端口
			acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));//添加消息过滤
			//设置读取缓冲区大小
			acceptor.getSessionConfig().setReadBufferSize(readBufferSize);
			//读取通道10秒没有反应则进入空闲
			acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, idleTime);	
			acceptor.setHandler(new ServerHandler());
			acceptor.bind(new InetSocketAddress(port));
			logger.info("server start success the port is "+port);
		} catch (Exception e) {
			logger.error("start server is error"+e.getMessage());
			e.printStackTrace();
		} 
			
		
		
	}
}
