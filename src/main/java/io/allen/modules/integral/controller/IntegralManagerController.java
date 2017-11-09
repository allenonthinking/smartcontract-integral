package io.allen.modules.integral.controller;


import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
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
import io.allen.modules.integral.entity.IntegralEntity;
import io.allen.modules.integral.service.IntegralService;
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
	private IntegralService integralService;
	
	@Autowired
	private SysUserService sysUserService;
	
	
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
	public R saveBalance(@RequestBody Map<String, Object> params){
		System.out.println(params);
		return R.ok();
	}
}
