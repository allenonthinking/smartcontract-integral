package io.allen.modules.generator.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 交易表
 * 
 * @author allen.liu
 * @date 2017-11-13 11:24:46
 */
public class BcTransactionEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//交易ID
	private String txId;
	//状态  0:未知  1:pending 2:sucess 3:fail
	private Integer status;
	//发起人地址
	private String fromAddress;
	//接收人地址
	private String toAddress;
	//合约地址
	private String contractAddress;
	//数据
	private String data;
	//金额
	private String amount;
	//精度
	private Long decimals;
	//真正转账金额
	private String value;
	//类型   1：普通转账   2：积分转账
	private Integer type;

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
	 * 设置：交易ID
	 */
	public void setTxId(String txId) {
		this.txId = txId;
	}
	/**
	 * 获取：交易ID
	 */
	public String getTxId() {
		return txId;
	}
	/**
	 * 设置：状态  0:未知  1:pending 2:sucess 3:fail
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：状态  0:未知  1:pending 2:sucess 3:fail
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：发起人地址
	 */
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
	/**
	 * 获取：发起人地址
	 */
	public String getFromAddress() {
		return fromAddress;
	}
	/**
	 * 设置：接收人地址
	 */
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}
	/**
	 * 获取：接收人地址
	 */
	public String getToAddress() {
		return toAddress;
	}
	/**
	 * 设置：合约地址
	 */
	public void setContractAddress(String contractAddress) {
		this.contractAddress = contractAddress;
	}
	/**
	 * 获取：合约地址
	 */
	public String getContractAddress() {
		return contractAddress;
	}
	/**
	 * 设置：数据
	 */
	public void setData(String data) {
		this.data = data;
	}
	/**
	 * 获取：数据
	 */
	public String getData() {
		return data;
	}
	/**
	 * 设置：金额
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}
	/**
	 * 获取：金额
	 */
	public String getAmount() {
		return amount;
	}
	/**
	 * 设置：精度
	 */
	public void setDecimals(Long decimals) {
		this.decimals = decimals;
	}
	/**
	 * 获取：精度
	 */
	public Long getDecimals() {
		return decimals;
	}
	/**
	 * 设置：真正转账金额
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * 获取：真正转账金额
	 */
	public String getValue() {
		return value;
	}
	/**
	 * 设置：类型   1：普通转账   2：积分转账
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：类型   1：普通转账   2：积分转账
	 */
	public Integer getType() {
		return type;
	}
}
