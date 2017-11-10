package io.allen.modules.sys.controller;


import io.allen.common.annotation.SysLog;
import io.allen.common.utils.PageUtils;
import io.allen.common.utils.Query;
import io.allen.common.utils.R;
import io.allen.common.validator.Assert;
import io.allen.common.validator.ValidatorUtils;
import io.allen.common.validator.group.AddGroup;
import io.allen.common.validator.group.UpdateGroup;
import io.allen.crypto.ECKey;
import io.allen.crypto.EthereumAccount;
import io.allen.crypto.KeystoreFormat;
import io.allen.modules.integral.entity.IntegralEntity;
import io.allen.modules.integral.service.IntegralService;
import io.allen.modules.sys.entity.SysUserEntity;
import io.allen.modules.sys.service.SysUserRoleService;
import io.allen.modules.sys.service.SysUserService;
import io.allen.modules.sys.shiro.ShiroUtils;

import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.spongycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * 
 * @author allen.liu
 * @date 2016年11月1日 
 */
@RestController
@RequestMapping("/sys/admin")
public class SysAdminController extends AbstractController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private IntegralService integralService;
	/**
	 * 系统用户列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:admin:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
//		List<SysUserEntity> userList = sysUserService.queryList(query);
//		int total = sysUserService.queryTotal(query);
		List<SysUserEntity> userList = sysUserService.querySystemList(query);
		int total = sysUserService.querySystemTotal(query);
		
		PageUtils pageUtil = new PageUtils(userList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 获取登录的用户信息
	 */
	@RequestMapping("/info")
	public R info(){
		return R.ok().put("user", getUser());
	}
	
 	/**
	 * 管理员积分账户
	 */
	@RequestMapping("/account")
	public R integralAccount(){
		SysUserEntity user = getUser();
		
		//获取用户积分账户
		IntegralEntity integral = integralService.queryByUserId(user.getUserId());
		
		return R.ok().put("integral", integral);
	}
	
	@SysLog("管理员绑定积分账户")
	@RequestMapping("/binding")
	//@RequiresPermissions("sys:admin:binding")
	public R save(@RequestBody Map<String, String> params){
		String prikey = params.get("prikey");
		String password = params.get("password");
		
  	 final ECKey key = ECKey.fromPrivate(new BigInteger(prikey,16));
        EthereumAccount account = new EthereumAccount();
        account.init(key);
        KeystoreFormat keystoreFormat = new KeystoreFormat();
        String content = keystoreFormat.toKeystore(key, password);
        final String address =Hex.toHexString((account.getAddress()));
        System.out.println(content);
        System.out.println(address);
//		integralService.bindingIntegralUser(integral, getUser());
		return R.ok();
		
	}
	
	/**
	 * 修改登录用户密码
	 */
	@SysLog("修改密码")
	@RequestMapping("/password")
	public R password(String password, String newPassword){
		Assert.isBlank(newPassword, "新密码不为能空");

		//原密码
		password = ShiroUtils.sha256(password, getUser().getSalt());
		//新密码
		newPassword = ShiroUtils.sha256(newPassword, getUser().getSalt());
				
		//更新密码
		int count = sysUserService.updatePassword(getUserId(), password, newPassword);
		if(count == 0){
			return R.error("原密码不正确");
		}
		
		return R.ok();
	}
	
	/**
	 * 用户信息
	 */
	@RequestMapping("/info/{userId}")
	@RequiresPermissions("sys:admin:info")
	public R info(@PathVariable("userId") Long userId){
		SysUserEntity user = sysUserService.queryObject(userId);
		
		//获取用户所属的角色列表
		List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
		user.setRoleIdList(roleIdList);
		
		return R.ok().put("user", user);
	}
	
	/**
	 * 保存用户
	 */
	@SysLog("保存用户")
	@RequestMapping("/save")
	@RequiresPermissions("sys:admin:save")
	public R save(@RequestBody SysUserEntity user){
		ValidatorUtils.validateEntity(user, AddGroup.class);
		if(user.getRoleIdList()==null ||user.getRoleIdList().isEmpty() ){
			return R.error("必须为用户指定角色");
		}else if(user.getRoleIdList().size()>1){
			return R.error("用户只能指定一个角色");
		}else{
			sysUserService.save(user);
			return R.ok();
		}
	}
	
	/**
	 * 修改用户
	 */
	@SysLog("修改用户")
	@RequestMapping("/update")
	@RequiresPermissions("sys:admin:update")
	public R update(@RequestBody SysUserEntity user){
		ValidatorUtils.validateEntity(user, UpdateGroup.class);
		if(user.getRoleIdList()==null ||user.getRoleIdList().isEmpty() ){
			return R.error("必须为用户指定角色");
		}else if(user.getRoleIdList().size()>1){
			return R.error("用户只能指定一个角色");
		}else{
			sysUserService.update(user);
			return R.ok();
		}
	}
	
	/**
	 * 删除用户
	 */
	@SysLog("删除用户")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:admin:delete")
	public R delete(@RequestBody Long[] userIds){
		if(ArrayUtils.contains(userIds, 1L)){
			return R.error("系统管理员不能删除");
		}
		
		if(ArrayUtils.contains(userIds, getUserId())){
			return R.error("当前用户不能删除");
		}
		
		sysUserService.deleteBatch(userIds);
		
		return R.ok();
	}
}
