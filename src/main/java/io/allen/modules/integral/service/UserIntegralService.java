package io.allen.modules.integral.service;

/**
 * 用户与积分对应关系
 * 
 * @author allen.liu
 * @date 2017年11月2日
 */
public interface UserIntegralService {
	
	void saveOrUpdate(Long userId, Long integralId);
}
