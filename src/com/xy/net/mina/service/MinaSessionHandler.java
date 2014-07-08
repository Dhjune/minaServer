package com.xy.net.mina.service;

import java.lang.reflect.InvocationTargetException;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xy.game.message.MessageManager;

/**
 * mina框架的接收消息类，继承于IoHandlerAdapter，即IoHandler
 */
public class MinaSessionHandler extends IoHandlerAdapter{
	
	private int number = 0;
	/**日志信息*/
	private final static Logger log = LoggerFactory.getLogger(MinaSessionHandler.class);
	
	
	/**
	 * 接收消息，并将消息发送给消息管理者
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 */
	@Override
	public void messageReceived(IoSession session, Object message) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		log.info("REQUEST_NUMBER: " + (++number));
		new MessageManager(session, message).msgTransfer();
		/*byte send = 1;
		System.out.println(send);
		session.write(send);*/
	}
	
	/**
	 * 在消息发送完之后调用，可用于计算发送消息的数量
	 */
	@Override
	public void messageSent(IoSession session, Object message){
		log.info("MESSAGE SENT: " + message.toString());
		log.info("SESSION　ROLEID: " + session.getAttribute("roleId"));
		log.info("SESSION　ROLENAME: " + session.getAttribute("roleName"));
	}
	
	/**
	 * 会话（session）对象被创建的时候调用
	 */
	@Override
	public void sessionCreated(IoSession session){
		MessageManager.sessionSet.put(session.getId(),session);
	}
	
	/**
	 * 连接被打开的时候调用
	 */
	@Override
	public void sessionOpened(IoSession session){
	}
	
	/**
	 *会话（session）关闭的时候调用 
	 */
	@Override
	public void sessionClosed(IoSession session){
		MessageManager.playerSet.remove((String)session.getAttribute("roleName"));
		MessageManager.sessionSet.remove(session.getId());
	}
	
	/**
	 * 会话通道处于空闲的时候调用
	 */
	@Override
	public void sessionIdle(IoSession session, IdleStatus status){
		log.info("session will be closed");
		session.close();
		MessageManager.sessionSet.remove(session.getId());
		//playerSet.remove(session.getRemoteAddress().toString());
		//	System.out.println( "session is free, IDLE " + session.getIdleCount(status));
	}
	
	/**
	 * 出现异常时处理，一般用来关闭会话
	 */
	@Override
	public void exceptionCaught(IoSession session, Throwable cause){
		MessageManager.sessionSet.remove(session.getId());
		/*session.write("session exception");
		session.write("session will been closed");
		session.close();*/
	}
}
