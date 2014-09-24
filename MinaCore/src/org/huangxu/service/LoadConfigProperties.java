package org.huangxu.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.imageio.stream.FileImageInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoadConfigProperties {

	public static Logger logger = LoggerFactory.getLogger(LoadConfigProperties.class);
	
	/**
	 * 读取单个value
	 * @param filePath 文件路径
	 * @param key  需要读取value的key
	 * @return
	 */
	public static String readSiglevalue(String filePath,String key){
		Properties pop = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(filePath));
			pop.load(in);
			String value =pop.getProperty(key);
			return value ;
		} catch (Exception e) {
			logger.error("laod properties error :"+e);
			e.printStackTrace();
		}
		return null ;
	}
	/**
	 * 读取所有key和value
	 * @param filePath 文件路径
	 * @return map key+value
	 */
	public static Map<String, String> readAllProperties(String filePath){
		Properties pop = new Properties();
		Map<String, String>  map = new HashMap<String, String>();
		File file = new File(filePath);
		File[] ff = file.listFiles();
		String realPath = "";
		for(File f:ff){
			if(f.getName().endsWith(".properties")){
				realPath = f.getName();
			}
		}
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(filePath+"/"+realPath));
			pop.load(in);
			Set<Entry<Object, Object>> en = pop.entrySet();
			Iterator<Entry<Object, Object>> ito = en.iterator();
			while(ito.hasNext()){
				Entry<Object, Object> sss = ito.next();
				String key = (String) sss.getKey();
				String value = pop.getProperty(key);
				map.put(key, value);
			}
		} catch (Exception e) {
			logger.error("laod properties error :"+e);
			e.printStackTrace();
		}
		return map ;
	}
}
