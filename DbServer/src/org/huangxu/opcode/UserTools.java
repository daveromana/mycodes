package org.huangxu.opcode;

import java.util.concurrent.ConcurrentHashMap;

import org.huangxu.data.UserData;

public class UserTools {

	public static ConcurrentHashMap<Long, UserData> userMap = new ConcurrentHashMap<Long, UserData>();
	public static void addUser(Long id,UserData user){
		userMap.put(id, user);
	}
}
