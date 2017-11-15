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
import io.allen.modules.generator.entity.BizGiftEntity;
import io.allen.modules.generator.service.BizGiftService;
import io.allen.modules.sys.controller.AbstractController;


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
	
}
