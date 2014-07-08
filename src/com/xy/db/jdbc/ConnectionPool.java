package com.xy.db.jdbc;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Properties;

import com.xy.common.Tools;


/**
 *JDBC连接池机制，提供获取连接和返回连接的方法
 */
public class ConnectionPool {
	
	/**空闲连接*/
	private static LinkedList<Connection> m_notUsedConnection = new LinkedList<Connection>();
	
	/**已经使用的连接*/
	private static HashSet<Connection> m_usedConnection = new HashSet<Connection>();
	
	/**配置文件实体*/
	private static Tools utility;
	
	/**数据库url*/
	private static String mysql_url = null;
	
	/**数据库用户名和密码信息*/
	private static Properties connInfo;
	
	/**最大连接数*/
	private static int MAX_CONNECTION = 4;
	
	/**出错标志*/
	static final boolean DEBUG = false;
	
	/**最后清理连接池的时间*/
	static private long m_lastClearm_lastClearClosedConnection = System.currentTimeMillis();
	
	/**检查连接关闭的时间间隔为5秒*/
	public static long CHECK_CLOSED_CONNECTION_TIME = 5000;

	
	
	static {
        try {
            initDriver();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
	
	
	
	/**
	 * 初始化JDBC驱动
	 */
	private static void initDriver() throws InstantiationException,IllegalAccessException, ClassNotFoundException {
		Driver driver = (Driver) Class.forName("com.mysql.jdbc.Driver").newInstance();
		try {
			DriverManager.registerDriver(driver);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化JDBC的配置信息
	 */
	public static void JDBCInit(){
		utility = new Tools();
		connInfo = new Properties();
		
		utility.getProperties("resource" + java.io.File.separator + "system.ini");
		mysql_url = utility.getStringFromProperty("jdbc_url");
		connInfo.put("user", utility.getStringFromProperty("jdbc_user"));
		connInfo.put("password", utility.getStringFromProperty("jdbc_pwd"));
		
		/**连接池初始投入五个连接缓冲，加快第一次服务端响应速度*/
		for(int i = 0; i < 10; i++){
			try {
				Connection conn = DriverManager.getConnection(mysql_url,connInfo);
				m_notUsedConnection.addLast(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 建立连接
	 */
	public synchronized static Connection getConnection() {
		// 关闭清除多余的连接
		clearClosedConnection();

		// 输出当前总连接数
		if(DEBUG)
			System.out.println("当前总连接数：" + getConnectionCount());

		// 寻找空闲的连接
		while (m_notUsedConnection.size() > 0) {
			try {
				Connection con = (Connection) m_notUsedConnection.removeFirst();

				if (con.isClosed()) {
					continue;
				}

				m_usedConnection.add(con);
				if (DEBUG) {
					// System.out.println("连接初始化成功");
				}
				return con;
			} catch (SQLException e) {
			}
		}

		// 没有找到，建立一些新的连接以供使用
		int newCount = getIncreasingConnectionCount();
		LinkedList<Connection> list = new LinkedList<Connection>();
		Connection con = null;

		for (int i = 0; i < newCount; i++) {
			con = getNewConnection();
			if (con != null) {
				list.add(con);
			}
		}

		// 没有成功建立连接，访问失败
		if (list.size() == 0)
			return null;

		// 成功建立连接，使用的加入used队列，剩下的加入notUsed队列
		con = (Connection) list.removeFirst();
		m_usedConnection.add(con);
		m_notUsedConnection.addAll(list);
		list.clear();

		return con;
	}

	/**
	 * 创建新连接
	 */
	public static Connection getNewConnection() {
		try {
			Connection con = DriverManager.getConnection(mysql_url,connInfo);
			return con;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将使用完的连接放回到连接池中
	 */
	static synchronized void pushConnectionBackToPool(Connection con) {
		boolean exist = m_usedConnection.remove(con);
		if (exist) {
			m_notUsedConnection.addLast(con);
		}
	}
	
	/**
	 * 关闭所有连接
	 */
	public static int close() {
		int count = 0;

		Iterator<Connection> iterator = m_notUsedConnection.iterator();
		while (iterator.hasNext()) {
			try {
				((Connection) iterator.next()).close();
				count++;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		m_notUsedConnection.clear();

		iterator = m_usedConnection.iterator();
		while (iterator.hasNext()) {
			try {
				((Connection) iterator.next()).close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		m_usedConnection.clear();

		return count;
	}
	
	/**
	 * 清除已经关闭的连接
	 */
	private static void clearClosedConnection(){
		long time = System.currentTimeMillis();

		/**时间出错*/
		if(time < m_lastClearm_lastClearClosedConnection){
			return;
		}

		/**时间差在间隔时间内*/
		if(time - m_lastClearm_lastClearClosedConnection < CHECK_CLOSED_CONNECTION_TIME){
			return;
		}

		m_lastClearm_lastClearClosedConnection = time;

		Iterator<Connection> iterator = m_notUsedConnection.iterator();

		while(iterator.hasNext()){
			Connection conn = (Connection)iterator.next();

			try{
				if(conn.isClosed())
					iterator.remove();
			}catch(SQLException e){
				iterator.remove();
				//e.printStackTrace();
				if(DEBUG){
					System.out.println("连接有问题，已断开");
				}
			}
		}

		// 清除多余的Connection
		int decrease = getDecreasingConnectionCount();

		while (decrease > 0 && m_notUsedConnection.size() > 0) {
			Connection con = (Connection) m_notUsedConnection.removeFirst();

			try {
				con.close();
			} catch (SQLException e) {

			}

			decrease--;
		}
	}

	/**
	 * 返回需要增加的连接数量
	 */
	public static int getIncreasingConnectionCount() {
		int count = 1;
		count = getConnectionCount() / 4;

		if (count < 1)
			count = 1;

		return count;
	}

	/**
	 * 返回需要清除的连接数量
	 */
	public static int getDecreasingConnectionCount() {
		int count = 0;

		if (getConnectionCount() > MAX_CONNECTION) {
			count = getConnectionCount() - MAX_CONNECTION;
		}
		
		return count;
	}

	/**
	 * 返回还没有使用的连接数量，用于分配
	 */
	public static synchronized int getNotUsedConnectionCount() {
		return m_notUsedConnection.size();
	}

	/**
	 * 返回已经使用的连接数量
	 */
	public static synchronized int getUsedConnectionCount() {
		return m_usedConnection.size();
	}

	/**
	 * 返回全部连接数量
	 */
	public static synchronized int getConnectionCount() {
		return m_notUsedConnection.size() + m_usedConnection.size();
	}

}

