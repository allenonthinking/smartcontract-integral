package io.allen.modules.generator.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.spongycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;

import io.allen.common.utils.MapUtils;
import io.allen.common.utils.PageUtils;
import io.allen.common.utils.Query;
import io.allen.common.utils.R;
import io.allen.crypto.ECKey;
import io.allen.crypto.KeystoreFormat;
import io.allen.modules.erc20.generated.CryptoUtils;
import io.allen.modules.erc20.generated.IntegralConfig;
import io.allen.modules.erc20.generated.TransactionResponse;
import io.allen.modules.erc20.service.ContractService;
import io.allen.modules.generator.entity.BcTransactionEntity;
import io.allen.modules.generator.entity.BizExchangeRecordEntity;
import io.allen.modules.generator.entity.BizGiftEntity;
import io.allen.modules.generator.service.BcTransactionService;
import io.allen.modules.generator.service.BizExchangeRecordService;
import io.allen.modules.generator.service.BizGiftService;
import io.allen.modules.integral.entity.IntegralEntity;
import io.allen.modules.integral.service.IntegralService;
import io.allen.modules.sys.controller.AbstractController;
import io.allen.modules.sys.entity.SysUserEntity;


/**
 * 礼品兑换
 * 
 * @author allen.liu
 * @date 2017-11-15 09:25:12
 */
@RestController
@RequestMapping("bizgift/exchange")
public class BizExchangeGiftController extends AbstractController {
	@Autowired
	private BizGiftService bizGiftService;
	
	@Autowired
	private IntegralService integralService;
	
	@Autowired
	private IntegralConfig integralConfig;
	
	@Autowired
	private BizExchangeRecordService bizExchangeRecordService;
	
	@Autowired
    private  ContractService contractService;
    
	@Autowired
	private BcTransactionService bcTransactionService;
	/**
	 * 查询可兑换礼品列表数据
	 * @param params
	 * @return
	 */
	@RequestMapping("/list")
	@RequiresPermissions("bizgift:exchange:list")
	public R exchangeList(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<BizGiftEntity> bizGiftList = bizGiftService.queryExchangeList(query);
		int total = bizGiftService.queryExchangeTotal(query);
		
		PageUtils pageUtil = new PageUtils(bizGiftList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{giftId}")
	//@RequiresPermissions("bizgift:exchange:info")
	public R info(@PathVariable("giftId") Long giftId){
		BizGiftEntity bizGift = bizGiftService.queryObject(giftId);
		
		SysUserEntity user = getUser();
		//获取用户积分账户
		IntegralEntity integral = integralService.queryByUserId(user.getUserId());
		
		if(integral == null || StringUtils.isBlank(integral.getAddress())) {
			return R.error("用户未绑定积分账户");
		}
		
		double integralBalance = integralService.getIntegralBalance(integralConfig.getContractAddress(), integral.getAddress());
		
		if(bizGift.getExchangePrice().doubleValue() > integralBalance) {
			return R.error("积分余额不足");
		}
		
		Map<String,Object> result = MapUtils.beanToMap(bizGift);
		result.put("integralBalance", integralBalance);
		return R.ok().put("bizGift",result);
	}
	
	/**
	 * 积分兑换礼品保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("bizgift:exchange:save")
	public R save(@RequestBody Map<String, String> params){
		// 获取参数
		int totalView = 0;
		double exchangePriceView = 0l;
		int count = 0;
		long giftId = 0;
		String password = null;
		double integralBalanceView = 0l;
		try {
			totalView = Integer.parseInt(params.get("total"));
			exchangePriceView = Double.parseDouble(params.get("exchangePrice"));
			integralBalanceView = Double.parseDouble(params.get("integralBalance"));
			count= Integer.parseInt(params.get("count"));
			password = params.get("password");
			giftId = Long.parseLong(params.get("giftId"));
		} catch (Exception e) {
			return R.error("参数错误");
		}
		// 检验参数是否超出额度
		if(exchangePriceView * count >integralBalanceView) {
			return R.error("积分余额不足");
		}
		if(count >totalView) {
			return R.error("库存不足");
		}
		
		// 检验密码
		IntegralEntity integral = integralService.queryByUserId(getUserId());
		String jytContractAddress = CryptoUtils.checkAddress(integralConfig.getContractAddress());
		ECKey key =null;
		String privateKey =null;
        try {
        	KeystoreFormat keystoreFormat = new KeystoreFormat();
        	key =  keystoreFormat.fromKeystore(integral.getKeystore(), password);
	        privateKey = Hex.toHexString(key.getPrivKeyBytes());
		} catch (Exception e) {
			return R.error("keystore解密失败，积分账户密码错误");
		}
        
		// 检验商品余额(数据库中真是库存)
		BizGiftEntity bizGift = bizGiftService.queryObject(giftId);
		if(count >bizGift.getTotal()) {
			return R.error("库存不足");
		}
		
		// 用户积分地址
		String fromAddress = CryptoUtils.checkAddress(integral.getAddress());
		// 积分回收地址
		String toAddress = CryptoUtils.checkAddress(integralConfig.getRecycleAddress());
		
		// 检验账户转账手续费是否足够
		if(!checkEthBalanceEnough(fromAddress)) {
			return R.error("积分账户转账手续费不足，请联系管理员");
		}
		// 检验积分余额
		double integralBalance = integralService.getIntegralBalance(jytContractAddress, fromAddress);
		if(integralBalanceView >integralBalance) {
			return R.error("积分余额发生变化请重写申请");
		}
		// 检验数据中真实兑换价格是否超出额度
		if(bizGift.getExchangePrice() * count >integralBalance) {
			return R.error("积分余额不足");
		}
		Long transferValue =new Integer(bizGift.getExchangePrice() * count).longValue();
		// 保存兑换记录  （缺少交易id） 减礼品数量减
		BizExchangeRecordEntity bizExchangeRecord = new BizExchangeRecordEntity();
		bizExchangeRecord.setTransferValue(transferValue);
		bizExchangeRecord.setAmount(count);
		bizExchangeRecord.setAddress(integral.getAddress());
		bizExchangeRecord.setCreateId(getUserId());
		bizExchangeRecordService.exchangeMinStock(bizExchangeRecord,giftId, count);
		
		String txId = null;
		// 发送积分交易
		try {
		  	// 精度
		  	BigInteger decimals = contractService.decimalsBigInteger(jytContractAddress);
		  	
		  	BigDecimal decimalIntegral = new BigDecimal(transferValue);
		  	// 小数点右移
		  	decimalIntegral = decimalIntegral.movePointRight(decimals.intValue());
		  	
		  	BigInteger balance = decimalIntegral.toBigInteger();
		  	
//			TransactionResponse resp = contractService.transferKey(privateKey, integralConfig.getContractAddress(), toAddress,balance);
			TransactionResponse resp = contractService.burnKey(privateKey, integralConfig.getContractAddress(), balance);
			 txId = resp.getTransactionHash();
			 BcTransactionEntity bcTransaction = new BcTransactionEntity();
			 bcTransaction.setTxId(txId);
			 bcTransaction.setStatus(0);
			 bcTransaction.setFromAddress(fromAddress);
			 bcTransaction.setContractAddress(jytContractAddress);
			 bcTransaction.setToAddress(toAddress);
			 bcTransaction.setAmount(transferValue.toString());
			 bcTransaction.setValue(balance.toString());
			 bcTransaction.setDecimals(decimals.longValue());
			 bcTransaction.setType(2);
			 bcTransactionService.save(bcTransaction);
			// 补全兑换记录里交易id
			bizExchangeRecord.setTxId(txId);
			bizExchangeRecordService.update(bizExchangeRecord);
//			contractService.transferKey(privateKey, integralConfig.getContractAddress(), address,balance);
//			return R.ok();
		} catch (Exception e) {
			return R.error(e.getMessage());
		}
		return R.ok().put("txid", txId);
	}
	private boolean checkEthBalanceEnough(String address) {
		try {
			BigInteger  accountBalance = contractService.etherBalanceOf(CryptoUtils.checkAddress(address));
			BigInteger normalAccountBalanceWei =  Convert.toWei(new BigDecimal("0.01"), Unit.ETHER).toBigInteger(); 
			// 默认账户小于最小定义余额，则转账
			if(accountBalance.compareTo(normalAccountBalanceWei) < 0 ) {
				return false ;
			}else {
				return true;
			}
		} catch (Exception e) {
			return false ;
		}
		
	}
}
