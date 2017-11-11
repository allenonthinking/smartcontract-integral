package io.allen.modules.generator.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 管理员用户与区块链账户对应关系
 * 
 * @author allen.liu
 * @date 2017-11-11 13:16:36
 */
public class BcUserAdminEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//用户ID
	private Long userId;
	//账户ID
	private Long accountId;

	/**
	 * 设置：
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：用户ID
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户ID
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：账户ID
	 */
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	/**
	 * 获取：账户ID
	 */
	public Long getAccountId() {
		return accountId;
	}
}
