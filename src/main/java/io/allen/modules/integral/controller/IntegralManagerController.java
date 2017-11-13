package io.allen.modules.integral.controller;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.spongycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.allen.common.annotation.SysLog;
import io.allen.common.utils.PageUtils;
import io.allen.common.utils.Query;
import io.allen.common.utils.R;
import io.allen.crypto.ECKey;
import io.allen.crypto.EthereumAccount;
import io.allen.crypto.KeystoreFormat;
import io.allen.modules.erc20.generated.CryptoUtils;
import io.allen.modules.erc20.generated.IntegralConfig;
import io.allen.modules.erc20.service.ContractService;
import io.allen.modules.generator.entity.BcAdminAccountEntity;
import io.allen.modules.generator.service.BcAdminAccountService;
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
	
	@Autowired
	private BcAdminAccountService bcAdminAccountService ;
	
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
	 * 积分详细信息
	 */
	@RequestMapping("/integralinfo")
	public R integralinfo(){
		String jytContractAddress = CryptoUtils.checkAddress(integralConfig.getContractAddress());
		// 积分
		BigInteger total = contractService.totalSupply(jytContractAddress);
		// 精度
		BigInteger decimals = contractService.decimalsBigInteger(jytContractAddress);
		
		BigDecimal decimalIntegral = new BigDecimal(total);
		// 小数点左移
		decimalIntegral = decimalIntegral.movePointLeft(decimals.intValue());
		
		// 保留小数点后俩位
		double totalintegralBalance = decimalIntegral.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		
		String name = contractService.name(jytContractAddress);
		String symbol = contractService.symbol(jytContractAddress);
		
		Map<String, String> result = new HashMap<String,String>();
		
		result.put("name", name);
		result.put("symbol", symbol);
		result.put("address", jytContractAddress);
		result.put("total", totalintegralBalance+"");
		
		return R.ok().put("integralInfo", result);
	}
	/**
	 * 发放积分
	 */
	@SysLog("发放积分")
	@RequestMapping("/savebalance")
	@RequiresPermissions("integral:manager:savebalance")
	public R saveBalance(@RequestBody Map<String, String> params){
		
		BcAdminAccountEntity bcaccount = bcAdminAccountService.queryByUserId(getUserId());
		
		if(bcaccount == null){
			return R.error("当前账户未授权区块链地址");
		}
		
		String fromAddress = CryptoUtils.checkAddress(bcaccount.getAddress());
		// 积分
		BigInteger integral = new BigInteger(params.get("balance"));
		
		String password = params.get("password");
		
		String address = CryptoUtils.checkAddress(params.get("address"));
		
		String jytContractAddress = CryptoUtils.checkAddress(integralConfig.getContractAddress());
		
		ECKey key =null;
        try {
        	KeystoreFormat keystoreFormat = new KeystoreFormat();
        	key =  keystoreFormat.fromKeystore(bcaccount.getKeystore(), password);
	        EthereumAccount account = new EthereumAccount();
	        account.init(key);
	        fromAddress = CryptoUtils.checkAddress(Hex.toHexString(account.getAddress()));
		} catch (Exception e) {
			return R.error("keystore解密失败，可能密码错误");
		}
        
       
        String privateKey = Hex.toHexString(key.getPrivKeyBytes());
		
		try {
			
		  	// 精度
		  	BigInteger decimals = contractService.decimalsBigInteger(jytContractAddress);
		  	BigDecimal decimalIntegral = new BigDecimal(integral);
		  	// 小数点右移
		  	decimalIntegral = decimalIntegral.movePointRight(decimals.intValue());
		  	
		  	BigInteger balance = decimalIntegral.toBigInteger();
		  	BigInteger owner = contractService.balanceOf(jytContractAddress, fromAddress);

		  	if(balance.compareTo(owner) >=0){
		  		return R.error("发放账户余额不足");
		  	}
//			TransactionResponse resp = contractService.transferKey(privateKey, integralConfig.getContractAddress(), address,balance);
//			return R.ok().put("txid", resp.getTransactionHash());
			contractService.transferKey(privateKey, integralConfig.getContractAddress(), address,balance);
			return R.ok();
		} catch (Exception e) {
			return R.error(e.getMessage());
		}
		
	}
	
}
