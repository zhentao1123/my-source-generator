
package com.demo.entity;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigInteger;
import java.lang.String;
import java.lang.Long;
import java.lang.Boolean;
import java.sql.Timestamp;
import java.lang.Integer;

import com.demo.entity.BaseEntity;

@Entity
@Table(name="memb_member")
public class MembMember extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * id
	 */
	private BigInteger id;
	
	/**
	 * 用户UUID
	 */
	private String uuid;
	
	/**
	 * 用户会员卡可见号码（商家唯一）
	 */
	private String memberNo;
	
	/**
	 * 商户ID
	 */
	private Long mchId;
	
	/**
	 * 商户UUID
	 */
	private String mchUuid;
	
	/**
	 * 微信ID
	 */
	private String wxId;
	
	/**
	 * 微信账户
	 */
	private String wxAccount;
	
	/**
	 * 微信昵称
	 */
	private String wxNickName;
	
	/**
	 * 微信头像
	 */
	private String wxAvatar;
	
	/**
	 * 昵称
	 */
	private String nickName;
	
	/**
	 * 真实姓名
	 */
	private String realName;
	
	/**
	 * 头像
	 */
	private String avatar;
	
	/**
	 * 性别0未知1男2女
	 */
	private Boolean sex;
	
	/**
	 * 手机号
	 */
	private String phone;
	
	/**
	 * 邮箱
	 */
	private String email;
	
	/**
	 * 生日
	 */
	private Timestamp birthday;
	
	/**
	 * 注册时间
	 */
	private Timestamp registerTime;
	
	/**
	 * 最近登录时间
	 */
	private Timestamp lastLoginTime;
	
	/**
	 * 最近登录客户端（1.web 2.android 3.ios）
	 */
	private Long lastLoginClient;
	
	/**
	 * 系统类型1常规2测试3模拟
	 */
	private Integer sysType;
	
	/**
	 * 状态
	 */
	private Long status;
	
	public BigInteger getId(){
		return this.id;
	}
	
	public void setId(BigInteger id){
		this.id = id;
	}
	
	public String getUuid(){
		return this.uuid;
	}
	
	public void setUuid(String uuid){
		this.uuid = uuid;
	}
	
	public String getMemberNo(){
		return this.memberNo;
	}
	
	public void setMemberNo(String memberNo){
		this.memberNo = memberNo;
	}
	
	public Long getMchId(){
		return this.mchId;
	}
	
	public void setMchId(Long mchId){
		this.mchId = mchId;
	}
	
	public String getMchUuid(){
		return this.mchUuid;
	}
	
	public void setMchUuid(String mchUuid){
		this.mchUuid = mchUuid;
	}
	
	public String getWxId(){
		return this.wxId;
	}
	
	public void setWxId(String wxId){
		this.wxId = wxId;
	}
	
	public String getWxAccount(){
		return this.wxAccount;
	}
	
	public void setWxAccount(String wxAccount){
		this.wxAccount = wxAccount;
	}
	
	public String getWxNickName(){
		return this.wxNickName;
	}
	
	public void setWxNickName(String wxNickName){
		this.wxNickName = wxNickName;
	}
	
	public String getWxAvatar(){
		return this.wxAvatar;
	}
	
	public void setWxAvatar(String wxAvatar){
		this.wxAvatar = wxAvatar;
	}
	
	public String getNickName(){
		return this.nickName;
	}
	
	public void setNickName(String nickName){
		this.nickName = nickName;
	}
	
	public String getRealName(){
		return this.realName;
	}
	
	public void setRealName(String realName){
		this.realName = realName;
	}
	
	public String getAvatar(){
		return this.avatar;
	}
	
	public void setAvatar(String avatar){
		this.avatar = avatar;
	}
	
	public Boolean getSex(){
		return this.sex;
	}
	
	public void setSex(Boolean sex){
		this.sex = sex;
	}
	
	public String getPhone(){
		return this.phone;
	}
	
	public void setPhone(String phone){
		this.phone = phone;
	}
	
	public String getEmail(){
		return this.email;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public Timestamp getBirthday(){
		return this.birthday;
	}
	
	public void setBirthday(Timestamp birthday){
		this.birthday = birthday;
	}
	
	public Timestamp getRegisterTime(){
		return this.registerTime;
	}
	
	public void setRegisterTime(Timestamp registerTime){
		this.registerTime = registerTime;
	}
	
	public Timestamp getLastLoginTime(){
		return this.lastLoginTime;
	}
	
	public void setLastLoginTime(Timestamp lastLoginTime){
		this.lastLoginTime = lastLoginTime;
	}
	
	public Long getLastLoginClient(){
		return this.lastLoginClient;
	}
	
	public void setLastLoginClient(Long lastLoginClient){
		this.lastLoginClient = lastLoginClient;
	}
	
	public Integer getSysType(){
		return this.sysType;
	}
	
	public void setSysType(Integer sysType){
		this.sysType = sysType;
	}
	
	public Long getStatus(){
		return this.status;
	}
	
	public void setStatus(Long status){
		this.status = status;
	}
}