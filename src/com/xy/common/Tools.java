package com.xy.common;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;

/**
 * 工具:1.获取资源配置文件； 2.获取资源配置文件的具体字段； 3.填充PreparedStatement；
 */
public class Tools {

	/**用于读取资源配置文件的bundle*/
	private static Properties properties;	

	
	/**
	 * 初始化配置文件的路径并获取最新的properties
	 */
	public void getProperties(String file){
		try{
			properties = new Properties();
			FileInputStream fis = new FileInputStream(new File(file));
			properties.load(fis);
		}catch(FileNotFoundException e){
			System.out.println(e.getMessage() + " " + file);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * 获取资源配置文件里定义的字段，例如：key = xxx
	 */
	public String getStringFromProperty(String key){
		String str = null;
		if(properties != null){
			str = properties.getProperty(key);
		}
		return str;
	}

	/**
	 * 填充PreparedStatement中的未知项
	 * @return 返回填充后的PreparedStatement
	 */
	public static PreparedStatement setPreStatementItems(PreparedStatement preStat, Object... inserts){
		try {
			if(inserts == null){
				return preStat;
			}
			int i = 1;
			for (Object v : inserts) {
				if(v.getClass().getSimpleName().equals("Integer")){//判断是整形
					preStat.setInt(i,(Integer)v);
					i++;
				}               	
				else if(v.getClass().getSimpleName().equals("String")){//判断是字符串
					preStat.setString(i, (String)v);
					i++;
				}
				else if(v.getClass().getSimpleName().equals("Long")){
					preStat.setLong(i, (long)v);
					i++;
				}
				else
					continue;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}  
		return preStat;
	}
	
	/**
	 * 获取字符串长度
	 */
	 public static int getWordCount(String s)
	    {
	        int length = 0;
	        for(int i = 0; i < s.length(); i++)
	        {
	            int ascii = Character.codePointAt(s, i);
	            if(ascii >= 0 && ascii <=255)
	                length++;
	            else
	                length += 2;
	                
	        }
	        return length;
	    }
	
	/**
	 * 获得一个随机序列 
	 */
	public static int[] getRandomSequence(int rangeSize, int queueSize){
		if(rangeSize < queueSize){
			return null;
		}
		Random rd = new Random();
		int[] sequence = new int[rangeSize];
		for (int i = 0; i < rangeSize; i++)
			sequence[i] = i;
		int[] output = new int[queueSize];
		int end = rangeSize-1;
		for (int i = 0; i < queueSize; i++){
			int num = rd.nextInt(end + 1);
			output[i] = sequence[num];
			sequence[num] = sequence[end];
			end--;
		}
		return output;
	}
}