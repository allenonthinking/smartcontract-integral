package io.allen.modules.integral.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.allen.modules.integral.dao.UserIntegralDao;
import io.allen.modules.integral.entity.UserIntegralEntity;
import io.allen.modules.integral.service.UserIntegralService;


/**
 * 积分
 * 
 * @author allen.liu
 * @date 2017年11月2日
 */
@Service("userIntegralService")
public class UserIntegralServiceImpl implements UserIntegralService {

	@Autowired
	private UserIntegralDao userIntegralDao;
	
	@Override
	@Transactional
	public void saveOrUpdate(Long userId, Long integralId) {
		// 删除用户积分绑定关系
		userIntegralDao.delete(userId);
		UserIntegralEntity userIntegral = new UserIntegralEntity();
		userIntegral.setUserId(userId);
		userIntegral.setIntegralId(integralId);
		userIntegralDao.save(userIntegral);
	}
	

}
