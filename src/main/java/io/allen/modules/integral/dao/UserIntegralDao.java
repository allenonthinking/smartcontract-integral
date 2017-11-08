package io.allen.modules.integral.dao;

import org.apache.ibatis.annotations.Mapper;

import io.allen.modules.integral.entity.UserIntegralEntity;
import io.allen.modules.sys.dao.BaseDao;

/**
 * 用户与积分对应关系
 * 
 * @author allen.liu
 * @date 2017年11月2日
 */
@Mapper
public interface UserIntegralDao extends BaseDao<UserIntegralEntity> {
	
}
