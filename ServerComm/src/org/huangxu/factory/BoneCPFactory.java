package org.huangxu.factory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
/**
 * 数据库连接工厂
 * @author robert
 *
 */
public final class BoneCPFactory implements IDBFactory {

	Logger logger = LoggerFactory.getLogger(BoneCPFactory.class);
	private BoneCP bonecp = null ;
	private Connection connection = null ;
	private static BoneCPFactory instance = new BoneCPFactory();
	
	public static BoneCPFactory getInstance(){
		return instance ;
	}
	@Override
	public Connection getConnection() {
		return instance.connection;
	}

	@Override
	public boolean initDB(String driverName, String url, String username,
			String password, int minConnections, int maxConnections) {
		try {
			Class.forName(driverName);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		try {
			//设置连接池配置信息 
			BoneCPConfig config = new BoneCPConfig();
			//数据库的JDBC URL  
			config.setJdbcUrl(url);
			//用户名
			config.setUsername(username);
			//密码
			config.setPassword(password);
			//最小连接数
			config.setMinConnectionsPerPartition(minConnections);
			//最大连接数
			config.setMaxConnectionsPerPartition(maxConnections);
//			config.setPartitionCount(10);
			//设置数据库连接池
			bonecp = new BoneCP(config);
			//从数据库连接池获取一个数据库连接
			connection = bonecp.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
			return false ;
		}
		return true;
	}

	public void freeConnections(Connection conn,PreparedStatement stmt,ResultSet  rs){
		try {
			if(rs!=null){
				rs.close();	
			}if(stmt!=null){
				stmt.close();
			}if(conn!=null){
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
