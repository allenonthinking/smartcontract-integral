package io.allen.modules.integral.dao;

import io.allen.modules.integral.entity.IntegralEntity;
import io.allen.modules.sys.dao.BaseDao;

/**
 * 积分管理
 * 
 * @author allen.liu
 * @date 2017年11月2日 
 */
public interface IntegralDao extends BaseDao<IntegralEntity> {
	/**
	 * 
	 * @param userId
	 * @return
	 */
	IntegralEntity queryByUserId(Long userId);
}
