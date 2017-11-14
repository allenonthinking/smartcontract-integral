package io.allen.modules.integral.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.allen.modules.integral.dao.IntegralDao;
import io.allen.modules.integral.entity.IntegralEntity;
import io.allen.modules.integral.service.IntegralService;
import io.allen.modules.integral.service.UserIntegralService;
import io.allen.modules.sys.entity.SysUserEntity;


/**
 * 积分
 * 
 * @author allen.liu
 * @date 2017年11月2日
 */
@Service("integralService")
public class IntegralServiceImpl implements IntegralService {
	
	@Autowired
	private IntegralDao integralDao;
	@Autowired
	private UserIntegralService userIntegralService;
	@Override
	public IntegralEntity queryByUserId(Long userId) {
		return integralDao.queryByUserId(userId);
	}

	@Override
	@Transactional
	public void bindingIntegralUser(IntegralEntity integral, SysUserEntity user) {
		integral.setCreateTime(new Date());
		integralDao.save(integral);
		userIntegralService.saveOrUpdate(user.getUserId(), integral.getIntegralId());
	}

	@Override
	public List<IntegralEntity> queryList(Map<String, Object> map) {
		return integralDao.queryList(map);
	}
	

}
