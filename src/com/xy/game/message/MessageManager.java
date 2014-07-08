package com.xy.game.message;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import net.sf.json.JSONObject;

import org.apache.mina.core.session.IoSession;

import com.xy.common.Tools;
import com.xy.game.message.handler.AbstractHandler;

public class MessageManager {
	
	/**消息类型配置文件*/
	private static Tools properties;
	
	/**会话*/
	private IoSession session;		
	
	/**消息*/
	private JSONObject message;	
	
	/**session集合*/
	public static HashMap<Long, IoSession> sessionSet = new HashMap<Long, IoSession>();
	
	/**已成功登陆的玩家session集合*/
	public  static HashMap<String, IoSession> playerSet = new HashMap<String, IoSession>();
	
	public MessageManager(IoSession session, Object message){
		this.session = session;
		this.message = JSONObject.fromObject(message.toString());
	}

	
	public static void init(){
		properties = new Tools();
		properties.getProperties("resource" + java.io.File.separator + "messageType.ini");
		return;
	}
	
	/**
	 * 接收消息，并将消息发送到相对应的消息处理者处理
	 */
	public void msgTransfer() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		//String s = properties.getStringFromProperty(message.getInt("msgType") + "");
		Class<?> transmission = Class.forName(properties.getStringFromProperty(message.getInt("msgType") + ""));
		Constructor<?> test = transmission.getConstructor(IoSession.class, JSONObject.class);
		AbstractHandler handler = (AbstractHandler) test.newInstance(session, message);
		handler.handle();
	}
}

