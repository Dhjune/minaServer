package com.xy.common.struct.constants;

/**
 * 客户端消息类型
 * 1xxx：代表人物相关消息； 2xxx：代表装备相关消息；3xxx：代表技能相关消息
 * 4xxx：代表伙伴相关消息； 5xxx：代表战斗相关消息; 6xxx: 代表物品相关信息
 * 第一位为大类，第二位为子类，后两位为具体消息
 */
public class MessageType {
	/**获取人物基本信息*/
	public static final int ROLE_GET = 1101;
	
	/**装备获取，测试*/
	public static final int EQUIPMENT_GET = 2100;
	
	/**装备解锁*/
	public static final int EQUIPMENT_UNLOCK = 2101;
	
	/**装备升级*/
	public static final int EQUIPMENT_UPGRADE = 2102;
	
	/**装备携带*/
	public static final int EQUIPMENT_CARRY = 2103;
	
	/**装备宝石镶嵌*/
	public static final int EQUIPMENT_EMBED = 2104;
	
	/**装备传承*/
	public static final int EQUIPMENT_INHERIT = 2105;
	
	/**技能获取,测试用*/
	public static final int SKILL_GET = 3100;
	
	/**技能学习*/
	public static final int SKILL_LEARN = 3101;
	
	/**技能升级*/
	public static final int SKILL_UPGRADE = 3102;
	
	/**技能携带*/
	public static final int SKILL_CARRY = 3103;
	
	/**获取伙伴*/
	public static final int PARTNER_GET = 4101;
	
	/**伙伴升级*/
	public static final int PARTNER_UPGRADE = 4102;
	
	/**伙伴招募*/
	public static final int PARTNER_RECRUIT = 4103;
	
	/**伙伴传承*/
	public static final int PARTNER_INHERIT = 4104;
	
	/**伙伴技能重置*/
	public static final int PARTNER_SKILL_RESET = 4105;
	
	/**伙伴上阵*/
	public static final int PARTNER_CHANGE_PARTNER = 4106;

  	/**pve战斗开始*/
  	public static final int PVE_BATTLE_START = 5101;
  	
  	/**pve战斗下一回合*/
  	public static final int PVE_BATTLE_NEXT_STEP = 5102;
  	
  	/**pve战斗结束开启宝箱*/
  	public static final int PVE_BATTLE_OPEN_CHEST = 5103;
  	
  	/**pvp战斗开始*/
  	public static final int PVP_BATTLE_START = 5201;
  	
  	/**pvp战斗下一回合*/
  	public static final int PVP_BATTLE_NEXT_STEP = 5202;
  	
  	/**pvp天梯匹配*/
  	public static final int PVP_BATTLE_LADDER_MATCH = 5203;
  	
  	/**增加挑战次数*/
  	public static final int PVP_ADD_LAST_TIMES = 5204;
  	
  	/**重置挑战时间*/
  	public static final int PVP_RESET_CHAL_TIME = 5205;
  	
  	/**获取物品信息*/
  	public static final int ITEM_GET = 6100;
  	
  	/**宝石合成*/
  	public static final int JEWEL_COMPOUND = 6101;
  	
  	/**符文合成*/
  	public static final int RUNE_COMPOUND = 6201;
}
