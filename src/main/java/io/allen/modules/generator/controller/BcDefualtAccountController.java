package io.allen.modules.generator.controller;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.spongycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.allen.common.annotation.SysLog;
import io.allen.common.utils.PageUtils;
import io.allen.common.utils.Query;
import io.allen.common.utils.R;
import io.allen.common.validator.ValidatorUtils;
import io.allen.common.validator.group.AddGroup;
import io.allen.crypto.ECKey;
import io.allen.crypto.EthereumAccount;
import io.allen.crypto.KeystoreFormat;
import io.allen.modules.generator.entity.BcDefualtAccountEntity;
import io.allen.modules.generator.service.BcDefualtAccountService;
import io.allen.modules.sys.shiro.ShiroUtils;


/**
 * 默认区块链账户
 * 
 * @author allen.liu
 * @date 2017-11-14 09:36:52
 */
@RestController
@RequestMapping("bcdefualtaccount")
public class BcDefualtAccountController {
	@Autowired
	private BcDefualtAccountService bcDefualtAccountService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("bcdefualtaccount:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<BcDefualtAccountEntity> bcDefualtAccountList = bcDefualtAccountService.queryList(query);
		int total = bcDefualtAccountService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(bcDefualtAccountList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{accountId}")
	@RequiresPermissions("bcdefualtaccount:info")
	public R info(@PathVariable("accountId") Long accountId){
		BcDefualtAccountEntity bcDefualtAccount = bcDefualtAccountService.queryObject(accountId);
		
		return R.ok().put("bcDefualtAccount", bcDefualtAccount);
	}
	
	/**
	 * 保存
	 */
	@SysLog("保存默认区块链账户")
	@RequestMapping("/save")
	@RequiresPermissions("bcdefualtaccount:save")
	public R save(@RequestBody BcDefualtAccountEntity bcDefualtAccount){
		ValidatorUtils.validateEntity(bcDefualtAccount, AddGroup.class);
		/**
		 * 检验私钥
		 */
		ECKey key = null;
        String content = null;
        String address = null;
		try {
			key = ECKey.fromPrivate(new BigInteger(bcDefualtAccount.getPrivateKey(), 16));
		} catch (Exception e) {
			return R.error("私钥格式错误");
		}
		/**
		 * 区块链账户
		 */
		EthereumAccount account = new EthereumAccount();
		account.init(key);
		
		
		//sha256加密
		String salt = RandomStringUtils.randomAlphanumeric(20);
		
		
		String realPassword= ShiroUtils.sha256(bcDefualtAccount.getPassword(), salt);
		
		KeystoreFormat keystoreFormat = new KeystoreFormat();
		content = keystoreFormat.toKeystore(key, realPassword);
		address = Hex.toHexString((account.getAddress()));
		
		
		bcDefualtAccount.setSalt(salt);
		bcDefualtAccount.setCreateTime(new Date());
		bcDefualtAccount.setAddress(address);
		bcDefualtAccount.setKeystore(content);
		bcDefualtAccount.setPassword(realPassword);
		bcDefualtAccount.setPrivateKey("");
		
		bcDefualtAccountService.save(bcDefualtAccount);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@SysLog("修改默认区块链账户")
	@RequestMapping("/update")
	@RequiresPermissions("bcdefualtaccount:update")
	public R update(@RequestBody BcDefualtAccountEntity bcDefualtAccount){
		bcDefualtAccountService.update(bcDefualtAccount);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@SysLog("删除默认区块链账户")
	@RequestMapping("/delete")
	@RequiresPermissions("bcdefualtaccount:delete")
	public R delete(@RequestBody Long[] accountIds){
		bcDefualtAccountService.deleteBatch(accountIds);
		
		return R.ok();
	}
	
}
