package io.allen.modules.generator.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 个人礼品兑换记录
 * 
 * @author allen.liu
 * @date 2017-11-15 10:55:46
 */
@RestController
@RequestMapping("bizexchangerecord/personal")
public class BizExchangePersonalRecordController extends AbstractController{
	@Autowired
	private BizExchangeRecordService bizExchangeRecordService;
	
	/**
	 * 查询个人兑换记录
	 * @param params
	 * @return
	 */
	@RequestMapping("/list")
	@RequiresPermissions("bizexchangerecord:personal:list")
	public R personalList(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<BizExchangeRecordEntity> bizExchangeRecordList = bizExchangeRecordService.queryPersonalList(params, getUserId());
		int total = bizExchangeRecordService.queryPersonalTotal(params, getUserId());
		
		PageUtils pageUtil = new PageUtils(bizExchangeRecordList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
}
