package io.allen.modules.generator.controller;

import java.util.List;
import java.util.Map;

import io.allen.common.utils.PageUtils;
import io.allen.common.utils.Query;
import io.allen.common.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.allen.modules.generator.entity.BcAdminAccountEntity;
import io.allen.modules.generator.service.BcAdminAccountService;


/**
 * 区块链账户
 * 
 * @author allen.liu
 * @date 2017-11-11 13:16:37
 */
@RestController
@RequestMapping("bcadminaccount")
public class BcAdminAccountController {
	@Autowired
	private BcAdminAccountService bcAdminAccountService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("bcadminaccount:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<BcAdminAccountEntity> bcAdminAccountList = bcAdminAccountService.queryList(query);
		int total = bcAdminAccountService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(bcAdminAccountList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{accountId}")
	@RequiresPermissions("bcadminaccount:info")
	public R info(@PathVariable("accountId") Long accountId){
		BcAdminAccountEntity bcAdminAccount = bcAdminAccountService.queryObject(accountId);
		
		return R.ok().put("bcAdminAccount", bcAdminAccount);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("bcadminaccount:save")
	public R save(@RequestBody BcAdminAccountEntity bcAdminAccount){
		bcAdminAccountService.save(bcAdminAccount);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("bcadminaccount:update")
	public R update(@RequestBody BcAdminAccountEntity bcAdminAccount){
		bcAdminAccountService.update(bcAdminAccount);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("bcadminaccount:delete")
	public R delete(@RequestBody Long[] accountIds){
		bcAdminAccountService.deleteBatch(accountIds);
		
		return R.ok();
	}
	
}
