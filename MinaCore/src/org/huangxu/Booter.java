package org.huangxu;

import org.huangxu.service.Configuration;
import org.huangxu.service.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Booter {

	public static Logger logger = LoggerFactory.getLogger(Booter.class);
	public static void main(String[] args) {
		String filePath= args[0];
		String serverName = args[1] ;
		if(filePath==null){
			logger.error("this file path is null , you must config the file path");
			return ;
		}
		if(serverName==null){
			logger.error("this server name is null , you must config the file path,eg:dbSever_");
			return ;
		}
		Configuration.getInstance().setup(filePath, serverName);
		Server.getInstance().init();

	}

}
