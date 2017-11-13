package io.allen.modules.generator.dao;

import io.allen.modules.generator.entity.BcUserAdminEntity;
import io.allen.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 管理员用户与区块链账户对应关系
 * 
 * @author allen.liu
 * @date 2017-11-11 13:16:36
 */
public interface BcUserAdminDao extends BaseDao<BcUserAdminEntity> {
	
}
