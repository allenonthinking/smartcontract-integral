package io.allen.modules.generator.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 区块链账户
 * 
 * @author allen.liu
 * @date 2017-11-11 13:16:37
 */
public class BcAdminAccountEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long accountId;
	//账户地址
	private String address;
	//keystore
	private String keystore;
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
}
