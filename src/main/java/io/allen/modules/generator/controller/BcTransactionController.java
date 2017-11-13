package io.allen.modules.generator.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.allen.common.utils.PageUtils;
import io.allen.common.utils.Query;
import io.allen.common.utils.R;
import io.allen.modules.generator.entity.BcTransactionEntity;
import io.allen.modules.generator.service.BcTransactionService;


/**
 * 交易表
 * 
 * @author allen.liu
 * @date 2017-11-13 11:24:46
 */
@RestController
@RequestMapping("bctransaction")
public class BcTransactionController {
	@Autowired
	private BcTransactionService bcTransactionService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("bctransaction:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<BcTransactionEntity> bcTransactionList = bcTransactionService.queryList(query);
		int total = bcTransactionService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(bcTransactionList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("bctransaction:info")
	public R info(@PathVariable("id") Long id){
		BcTransactionEntity bcTransaction = bcTransactionService.queryObject(id);
		
		return R.ok().put("bcTransaction", bcTransaction);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("bctransaction:save")
	public R save(@RequestBody BcTransactionEntity bcTransaction){
		bcTransactionService.save(bcTransaction);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("bctransaction:update")
	public R update(@RequestBody BcTransactionEntity bcTransaction){
		bcTransactionService.update(bcTransaction);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("bctransaction:delete")
	public R delete(@RequestBody Long[] ids){
		bcTransactionService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
