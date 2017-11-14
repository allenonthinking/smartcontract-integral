package io.allen.modules.generator.entity;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

import io.allen.common.validator.group.AddGroup;
import io.allen.common.validator.group.UpdateGroup;



/**
 * 默认区块链账户
 * 
 * @author allen.liu
 * @date 2017-11-14 09:36:52
 */
public class BcDefaultAccountEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long accountId;
	//账户名
	@NotBlank(message="账户名不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String accountName;
	//密码
	@NotBlank(message="密码不能为空", groups = AddGroup.class)
	private String password;
	//私钥
	@NotBlank(message="私钥不能为空", groups = AddGroup.class)
	private String privateKey;
	//盐
	private String salt;
	//账户地址
	private String address;
	//keystore
	private String keystore;
	//状态  0：禁用   1：正常
	private Integer status;
	//类型   1:基础发放    2:积分发放
	private Integer type;
	//创建时间
	private Date createTime;

	/**
	 * 设置：
	 */
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	/**
	 * 获取：
	 */
	public Long getAccountId() {
		return accountId;
	}
	/**
	 * 设置：账户名
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	/**
	 * 获取：账户名
	 */
	public String getAccountName() {
		return accountName;
	}
	/**
	 * 设置：密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 获取：密码
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 设置：盐
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}
	/**
	 * 获取：盐
	 */
	public String getSalt() {
		return salt;
	}
	/**
	 * 设置：账户地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取：账户地址
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 设置：keystore
	 */
	public void setKeystore(String keystore) {
		this.keystore = keystore;
	}
	/**
	 * 获取：keystore
	 */
	public String getKeystore() {
		return keystore;
	}
	/**
	 * 设置：状态  0：禁用   1：正常
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：状态  0：禁用   1：正常
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：类型   1:基础发放    2:积分发放
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：类型   1:基础发放    2:积分发放
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	public String getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
}
