package io.allen.modules.integral.service;

import io.allen.modules.integral.entity.IntegralEntity;
import io.allen.modules.sys.entity.SysUserEntity;

/**
 * 积分
 * 
 * @author allen.liu
 * @date 2017年11月2日 
 */
public interface IntegralService {
	IntegralEntity queryByUserId(Long userId);
	
	void bindingIntegralUser(IntegralEntity  integral , SysUserEntity user);
}
