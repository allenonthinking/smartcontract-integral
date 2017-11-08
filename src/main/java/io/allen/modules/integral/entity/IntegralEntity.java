package io.allen.modules.integral.entity;


import java.io.Serializable;
import java.util.Date;

/**
 * 积分
 * 
 * @author allen.liu
 * @date 2017年11月2日
 */
public class IntegralEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 积分ID
	 */
	private Long integralId;

	/**
	 * 积分地址
	 */
	private String address;

	/**
	 * keystore-json
	 */
	private String keystore;

	/**
	 * 创建时间
	 */
	private Date createTime;


	public Long getIntegralId() {
		return integralId;
	}


	public void setIntegralId(Long integralId) {
		this.integralId = integralId;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getKeystore() {
		return keystore;
	}


	public void setKeystore(String keystore) {
		this.keystore = keystore;
	}


	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
