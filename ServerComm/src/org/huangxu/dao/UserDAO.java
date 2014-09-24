package org.huangxu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.huangxu.data.UserData;
import org.huangxu.factory.BoneCPFactory;

import com.mysql.jdbc.Statement;

public class UserDAO {

	private static UserDAO userDao = new UserDAO();
	
	public static UserDAO getInstance(){
		if(userDao==null){
			userDao = new UserDAO();
			return userDao ;
		}else{
			return userDao ;
		}
	}
	public List<UserData> selectAllUser(){
		List<UserData> list = new ArrayList<UserData>() ;
		Connection conn = null ;
		PreparedStatement stmt = null ;
		ResultSet  rs = null ;
		conn = BoneCPFactory.getInstance().getConnection();
		String sql = "select * from user";
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			if(rs.next()){
				list.add(getUserData(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list ;
	}
	public UserData selectUserByNameAndPass(String  username,String password) {
		UserData user =null;
		Connection conn = null ;
		PreparedStatement stmt = null ;
		ResultSet  rs = null ;
		conn = BoneCPFactory.getInstance().getConnection();
		String sql = "select * from user where username=? and password=?";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			stmt.setString(2, password);
			rs = stmt.executeQuery();
			if(rs.next()){
				user = new UserData();
				user.setUid(rs.getInt("uid"));
				user.setUserName(rs.getString("username"));
				user.setPassword(rs.getString("password"));	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user ;
	}
	public UserData selectUserByName(String  username) {
		UserData user =null;
		Connection conn = null ;
		PreparedStatement stmt = null ;
		ResultSet  rs = null ;
		conn = BoneCPFactory.getInstance().getConnection();
		String sql = "select * from user where username=? ";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			rs = stmt.executeQuery();
			if(rs.next()){
				user = new UserData();
				user.setUid(rs.getInt("uid"));
				user.setUserName(rs.getString("username"));
				user.setPassword(rs.getString("password"));	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user ;
	}
	public UserData getUserData(ResultSet rs) throws SQLException{
		UserData user = new UserData();
		user.setUid(rs.getInt("uid"));
		user.setUserName(rs.getString("username"));
		user.setPassword(rs.getString("password"));
		return user ;
	}
	public int registUser(int uid,String username,String password){
		int result = 0 ;
		Connection conn = null ;
		PreparedStatement stmt = null ;
		ResultSet  rs = null ;
		conn = BoneCPFactory.getInstance().getConnection();
		String sql = "insert into user(uid,username,password) values(?,?,?)";
		try {
			stmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, uid);
			stmt.setString(2, username);
			stmt.setString(3, password);
			if(stmt.executeUpdate()>0){
				rs = stmt.getGeneratedKeys();
				if(rs.next()){
					result = rs.getInt(1);
				}
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result ;
	}
}
