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
import io.allen.modules.generator.entity.BizGiftEntity;
import io.allen.modules.generator.service.BizGiftService;
import io.allen.modules.sys.controller.AbstractController;


/**
 * 礼品表
 * 
 * @author allen.liu
 * @date 2017-11-15 09:25:12
 */
@RestController
@RequestMapping("bizgift")
public class BizGiftController extends AbstractController {
	@Autowired
	private BizGiftService bizGiftService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("bizgift:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<BizGiftEntity> bizGiftList = bizGiftService.queryList(query);
		int total = bizGiftService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(bizGiftList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{giftId}")
	@RequiresPermissions("bizgift:info")
	public R info(@PathVariable("giftId") Long giftId){
		BizGiftEntity bizGift = bizGiftService.queryObject(giftId);
		
		return R.ok().put("bizGift", bizGift);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("bizgift:save")
	public R save(@RequestBody BizGiftEntity bizGift){
		bizGift.setCreateId(getUser().getUserId());
		bizGiftService.save(bizGift);
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("bizgift:update")
	public R update(@RequestBody BizGiftEntity bizGift){
		bizGiftService.update(bizGift);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("bizgift:delete")
	public R delete(@RequestBody Long[] giftIds){
		bizGiftService.deleteBatch(giftIds);
		
		return R.ok();
	}
	
}
