package org.huangxu.service;

import java.util.Map;

public class Configuration {
	//service name 
	private String serviceName ; 
	//端口号
	private String port ;
	//多少时间进入空闲
	private String idleTime ;
	//设置读取缓冲区大小
	private String readBufferSize ;
	//驱动名
	private String driverName ;
	//url
	private String  jdbcUrl ;
	//username 
	private String userName ;
	//password
	private String password ;
	//minConnections
	private int minConnections ;
	//maxConnections
	private int maxConnections ;
	
	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	
	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getMinConnections() {
		return minConnections;
	}

	public void setMinConnections(int minConnections) {
		this.minConnections = minConnections;
	}

	public int getMaxConnections() {
		return maxConnections;
	}

	public void setMaxConnections(int maxConnections) {
		this.maxConnections = maxConnections;
	}

	public String getIdleTime() {
		return idleTime;
	}

	public void setIdleTime(String idleTime) {
		this.idleTime = idleTime;
	}

	public String getReadBufferSize() {
		return readBufferSize;
	}

	public void setReadBufferSize(String readBufferSize) {
		this.readBufferSize = readBufferSize;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
	private static Configuration instance = new Configuration();

	public static Configuration getInstance() {
		
		return instance;
	}

	public String getServiceName() {
		
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public void setup(String filePath,String serverName){
		Map<String, String> map = LoadConfigProperties.readAllProperties(filePath);
		String prefix = serverName ;
		if(map!=null){
			this.serviceName=map.get(prefix+"service");
			this.port = map.get(prefix+"port");
			this.idleTime = map.get(prefix+"idleTime");
			this.readBufferSize = map.get(prefix+"readBufferSize");	
			this.driverName = map.get(prefix+"driverName");
			this.jdbcUrl = map.get(prefix+"jdbcUrl");
			this.userName = map.get(prefix+"userName");
			this.password = map.get(prefix+"password");
			if(map.get(prefix+"minConnections")!=null){
				this.minConnections =Integer.parseInt(map.get(prefix+"minConnections"));
			}
			if(map.get(prefix+"maxConnections")!=null){
				this.maxConnections =Integer.parseInt(map.get(prefix+"maxConnections"));	
			}
			
		}
		
	}
}
