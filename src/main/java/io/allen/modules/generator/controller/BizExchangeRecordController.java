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
import io.allen.modules.generator.entity.BizExchangeRecordEntity;
import io.allen.modules.generator.service.BizExchangeRecordService;
import io.allen.modules.sys.controller.AbstractController;


/**
 * 礼品兑换记录
 * 
 * @author allen.liu
 * @date 2017-11-15 10:55:46
 */
@RestController
@RequestMapping("bizexchangerecord")
public class BizExchangeRecordController extends AbstractController{
	@Autowired
	private BizExchangeRecordService bizExchangeRecordService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("bizexchangerecord:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<BizExchangeRecordEntity> bizExchangeRecordList = bizExchangeRecordService.queryList(query);
		int total = bizExchangeRecordService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(bizExchangeRecordList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("bizexchangerecord:info")
	public R info(@PathVariable("id") Long id){
		BizExchangeRecordEntity bizExchangeRecord = bizExchangeRecordService.queryObject(id);
		
		return R.ok().put("bizExchangeRecord", bizExchangeRecord);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("bizexchangerecord:save")
	public R save(@RequestBody BizExchangeRecordEntity bizExchangeRecord){
		bizExchangeRecordService.save(bizExchangeRecord);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("bizexchangerecord:update")
	public R update(@RequestBody BizExchangeRecordEntity bizExchangeRecord){
		bizExchangeRecordService.update(bizExchangeRecord);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("bizexchangerecord:delete")
	public R delete(@RequestBody Long[] ids){
		bizExchangeRecordService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
