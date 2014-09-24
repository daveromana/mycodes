package org.huangxu.factory;

import java.sql.Connection;
/**
 * 数据库工厂
 * @author robert
 *
 */
public interface IDBFactory {

	/**
	 *  取的数据库连接
	 * @return
	 */
	public Connection getConnection();
	/**
	 * 初始化数据库连接
	 * @param driverName 驱动名称
	 * @param url jdbc地址
	 * @param username 数据库用户名
	 * @param password 数据库密码
	 * @param minConnections 最小连接数
	 * @param maxConnections 最大连接数
	 * @return 成功true，失败false
	 */
	public boolean initDB(String driverName,String url ,String username,String password,int minConnections,int maxConnections);
}
