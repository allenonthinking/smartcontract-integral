package io.allen.modules.integral.controller;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.allen.common.annotation.SysLog;
import io.allen.common.utils.PageUtils;
import io.allen.common.utils.Query;
import io.allen.common.utils.R;
import io.allen.modules.erc20.generated.CryptoUtils;
import io.allen.modules.erc20.generated.IntegralConfig;
import io.allen.modules.erc20.service.ContractService;
import io.allen.modules.sys.controller.AbstractController;
import io.allen.modules.sys.entity.SysUserEntity;
import io.allen.modules.sys.service.SysUserService;


/**
 * 用户积分管理
 * 
 * @author allen.liu
 * @date 2017-11-8
 */
@RestController
@RequestMapping("/integral/manager")
public class IntegralManagerController extends AbstractController{
	
	@Autowired
	private IntegralConfig integralConfig;
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
    private  ContractService contractService;
	
 	/**
	 * 积分账户列表
	 */
	@RequestMapping("/list")
	//@RequiresPermissions("integral:manager:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<SysUserEntity> userList = sysUserService.queryIntegralAccountList(query);
		int total = sysUserService.queryIntegralAccountTotal(query);
		PageUtils pageUtil = new PageUtils(userList, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 发放积分
	 */
	@SysLog("发放积分")
	@RequestMapping("/savebalance")
//	@RequiresPermissions("integral:manager:savebalance")
	public R saveBalance(@RequestBody Map<String, String> params){
		
		try {
			String privateKey = "41a6bc292a701c8da0529cafd8d339bb2ad5372d0695c5ba36be57530d4f4668";
			
			// 积分
			BigInteger integral = new BigInteger(params.get("balance"));
		  	String jytContractAddress = CryptoUtils.checkAddress(integralConfig.getContractAddress());
		  	// 精度
		  	BigInteger decimals = contractService.decimalsBigInteger(jytContractAddress);
		  	BigDecimal decimalIntegral = new BigDecimal(integral);
		  	// 小数点右移
		  	decimalIntegral = decimalIntegral.movePointRight(decimals.intValue());
		  	
		  	BigInteger balance = decimalIntegral.toBigInteger();
			
			String address = CryptoUtils.checkAddress(params.get("address"));
//			TransactionResponse resp = contractService.transferKey(privateKey, integralConfig.getContractAddress(), address,balance);
//			return R.ok().put("txid", resp.getTransactionHash());
			contractService.transferKey(privateKey, integralConfig.getContractAddress(), address,balance);
			return R.ok();
		} catch (Exception e) {
			return R.error(e.getMessage());
		}
		
	}
	
}
