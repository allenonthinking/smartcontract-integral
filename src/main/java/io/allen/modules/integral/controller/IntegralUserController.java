package io.allen.modules.integral.controller;


import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.allen.common.annotation.SysLog;
import io.allen.common.utils.R;
import io.allen.common.validator.ValidatorUtils;
import io.allen.common.validator.group.AddGroup;
import io.allen.modules.integral.entity.IntegralEntity;
import io.allen.modules.integral.service.IntegralService;
import io.allen.modules.sys.controller.AbstractController;
import io.allen.modules.sys.entity.SysUserEntity;


/**
 * 普通用户积分管理
 * 
 * @author allen.liu
 * @date 2017-11-2
 */
@RestController
@RequestMapping("/integral/user")
public class IntegralUserController extends AbstractController{
	
	@Autowired
	private IntegralService integralService;
 	/**
	 * 用户积分账户
	 */
	@RequestMapping("/account")
	@RequiresPermissions("integral:user:account")
	public R info(){
		SysUserEntity user = getUser();
		
		//获取用户积分账户
		IntegralEntity integral = integralService.queryByUserId(user.getUserId());
		
		return R.ok().put("integral", integral);
	}
	
	@SysLog("绑定积分账户")
	@RequestMapping("/binding")
	@RequiresPermissions("integral:user:binding")
	public R save(@RequestBody IntegralEntity integral){
		ValidatorUtils.validateEntity(integral, AddGroup.class);
		integralService.bindingIntegralUser(integral, getUser());
		return R.ok();
		
	}
}
