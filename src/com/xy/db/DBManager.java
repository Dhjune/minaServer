package com.xy.db;

/**
 * 内存数据库管理者
 */
public class DBManager {
	
	public static void dbInit(){
		/**角色相应等级属性内存数据库初始化*/
		RoleAttributeDB.roleAttrInit();
		System.out.println("角色相应等级属性内存数据库初始化成功！");
		return;
	}
}
