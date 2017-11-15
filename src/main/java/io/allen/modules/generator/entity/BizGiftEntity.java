package io.allen.modules.generator.entity;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

import io.allen.common.validator.group.AddGroup;
import io.allen.common.validator.group.UpdateGroup;



/**
 * 礼品表
 * 
 * @author allen.liu
 * @date 2017-11-15 09:25:12
 */
public class BizGiftEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//礼品id
	private Long giftId;
	//礼品名称
	@NotBlank(message="礼品名不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String giftName;
	//别名
	@NotBlank(message="别名不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String nickname;
	//礼品主图
	private String image;
	//链接地址
	private String url;
	//积分兑换价（1积分为单位）
	@NotBlank(message="积分兑换价格不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private Integer exchangePrice;
	//采购价（以元为单位）
	private Integer buyPrice;
	//数量
	private Integer total;
	//删除 1是 2否
	private Integer delState;
	//简要描述
	private String simpleDescribe;
	//详细描述
	private String detailDescribe;
	//上架标志 1 已上架 0 未上架
	private Integer isMarketable;
	//创建时间
	private Date createTime;
	//创建者id
	private Long createId;

	/**
	 * 设置：礼品id
	 */
	public void setGiftId(Long giftId) {
		this.giftId = giftId;
	}
	/**
	 * 获取：礼品id
	 */
	public Long getGiftId() {
		return giftId;
	}
	/**
	 * 设置：礼品名称
	 */
	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}
	/**
	 * 获取：礼品名称
	 */
	public String getGiftName() {
		return giftName;
	}
	/**
	 * 设置：别名
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	/**
	 * 获取：别名
	 */
	public String getNickname() {
		return nickname;
	}
	/**
	 * 设置：礼品主图
	 */
	public void setImage(String image) {
		this.image = image;
	}
	/**
	 * 获取：礼品主图
	 */
	public String getImage() {
		return image;
	}
	/**
	 * 设置：链接地址
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * 获取：链接地址
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * 设置：积分兑换价（1积分为单位）
	 */
	public void setExchangePrice(Integer exchangePrice) {
		this.exchangePrice = exchangePrice;
	}
	/**
	 * 获取：积分兑换价（1积分为单位）
	 */
	public Integer getExchangePrice() {
		return exchangePrice;
	}
	/**
	 * 设置：采购价（以元为单位）
	 */
	public void setBuyPrice(Integer buyPrice) {
		this.buyPrice = buyPrice;
	}
	/**
	 * 获取：采购价（以元为单位）
	 */
	public Integer getBuyPrice() {
		return buyPrice;
	}
	/**
	 * 设置：数量
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}
	/**
	 * 获取：数量
	 */
	public Integer getTotal() {
		return total;
	}
	/**
	 * 设置：删除 1是 2否
	 */
	public void setDelState(Integer delState) {
		this.delState = delState;
	}
	/**
	 * 获取：删除 1是 2否
	 */
	public Integer getDelState() {
		return delState;
	}
	/**
	 * 设置：简要描述
	 */
	public void setSimpleDescribe(String simpleDescribe) {
		this.simpleDescribe = simpleDescribe;
	}
	/**
	 * 获取：简要描述
	 */
	public String getSimpleDescribe() {
		return simpleDescribe;
	}
	/**
	 * 设置：详细描述
	 */
	public void setDetailDescribe(String detailDescribe) {
		this.detailDescribe = detailDescribe;
	}
	/**
	 * 获取：详细描述
	 */
	public String getDetailDescribe() {
		return detailDescribe;
	}
	/**
	 * 设置：上架标志 1 已上架 0 未上架
	 */
	public void setIsMarketable(Integer isMarketable) {
		this.isMarketable = isMarketable;
	}
	/**
	 * 获取：上架标志 1 已上架 0 未上架
	 */
	public Integer getIsMarketable() {
		return isMarketable;
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
	 * 设置：创建者id
	 */
	public void setCreateId(Long createId) {
		this.createId = createId;
	}
	/**
	 * 获取：创建者id
	 */
	public Long getCreateId() {
		return createId;
	}
}
