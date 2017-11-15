package io.allen.modules.generator.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 礼品兑换记录
 * 
 * @author allen.liu
 * @date 2017-11-15 10:55:46
 */
public class BizExchangeRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private Long id;
	//交易
	private String txId;
	//转账积分数额
	private Long transferValue;
	//兑换数量
	private Integer amount;
	//创建时间
	private Date createTime;
	//账户地址
	private String address;
	//兑换人id
	private Long createId;

	/**
	 * 设置：id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：交易
	 */
	public void setTxId(String txId) {
		this.txId = txId;
	}
	/**
	 * 获取：交易
	 */
	public String getTxId() {
		return txId;
	}
	/**
	 * 设置：转账积分数额
	 */
	public void setTransferValue(Long transferValue) {
		this.transferValue = transferValue;
	}
	/**
	 * 获取：转账积分数额
	 */
	public Long getTransferValue() {
		return transferValue;
	}
	/**
	 * 设置：兑换数量
	 */
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	/**
	 * 获取：兑换数量
	 */
	public Integer getAmount() {
		return amount;
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
	 * 设置：兑换人id
	 */
	public void setCreateId(Long createId) {
		this.createId = createId;
	}
	/**
	 * 获取：兑换人id
	 */
	public Long getCreateId() {
		return createId;
	}
}
