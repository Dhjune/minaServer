package com.xy.common.struct;

public class RoleAttribute {

	private int id;				//id,代表相应角色相应等级，如1001，代表等级1的骑士
	private int hp;				//血量
	private int atk;			//攻击
	private int pDef;			//物理防御
	private int mDef;			//魔法防御
	private int hit;			//命中
	private int antiHit;		//闪避
	private int crt;			//暴击
	private int speed;			//速度
	
	public int getId() {
		return id;
	}
	public int getHp() {
		return hp;
	}
	public int getpDef() {
		return pDef;
	}
	public int getmDef() {
		return mDef;
	}
	public int getHit() {
		return hit;
	}
	public int getAntiHit() {
		return antiHit;
	}
	public int getCrt() {
		return crt;
	}
	public int getSpeed() {
		return speed;
	}
	public int getAtk() {
		return atk;
	}
	
	
	public void setId(int id) {
		this.id = id;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public void setpDef(int pDef) {
		this.pDef = pDef;
	}
	public void setmDef(int mDef) {
		this.mDef = mDef;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public void setAntiHit(int antiHit) {
		this.antiHit = antiHit;
	}
	public void setCrt(int crt) {
		this.crt = crt;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public void setAtk(int atk) {
		this.atk = atk;
	}
}
