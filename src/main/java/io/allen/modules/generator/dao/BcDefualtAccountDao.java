package io.allen.modules.generator.dao;

import org.apache.ibatis.annotations.Mapper;

import io.allen.modules.generator.entity.BcDefualtAccountEntity;
import io.allen.modules.sys.dao.BaseDao;

/**
 * 默认区块链账户
 * 
 * @author allen.liu
 * @date 2017-11-14 09:36:52
 */
@Mapper
public interface BcDefualtAccountDao extends BaseDao<BcDefualtAccountEntity> {
	
}
