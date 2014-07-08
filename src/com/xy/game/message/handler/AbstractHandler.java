package com.xy.game.message.handler;

import net.sf.json.JSONObject;

import org.apache.mina.core.session.IoSession;

/**
 * 包含会话和消息及处理函数申明的抽象消息处理器
 */
public abstract class AbstractHandler {
	/**会话*/
	public IoSession session;
	
	/**消息*/
	public JSONObject message;
	
	public AbstractHandler(){}
	
	public AbstractHandler(IoSession session, JSONObject message){
		this.session = session;
		this.message = message;
	}
	
	public abstract void handle();
}
