package com.xy.db;

import java.util.ArrayList;
import java.util.HashMap;

import com.xy.common.struct.RoleAttribute;
import com.xy.db.dao.RoleDAO;

public class RoleAttributeDB {
	
	/**角色属性*/
	public static HashMap<Integer, RoleAttribute> roleAttr = new HashMap<Integer, RoleAttribute> ();
	
	/**将角色相应等级属性数据加载到内存中*/
	public static void roleAttrInit(){
		if(roleAttr == null){
			roleAttr = new HashMap<Integer, RoleAttribute>();
		}
		
		ArrayList<RoleAttribute> roleAttrList = new RoleDAO().getAllRoleAttr();
		for(int i = 0; i < roleAttrList.size(); i ++){
			roleAttr.put(roleAttrList.get(i).getId(), roleAttrList.get(i));
		}
		return ;
	}
	
}
