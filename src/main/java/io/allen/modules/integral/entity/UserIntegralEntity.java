package io.allen.modules.integral.entity;


import java.io.Serializable;

/**
 * 用户与积分账户对应关系
 * 
 * @author allen.liu
 * @date 2017年11月02日 
 */
public class UserIntegralEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;

	/**
	 * 用户ID
	 */
	private Long userId;

	/**
	 * 积分ID
	 */
	private Long integralId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getIntegralId() {
		return integralId;
	}

	public void setIntegralId(Long integralId) {
		this.integralId = integralId;
	}

}
