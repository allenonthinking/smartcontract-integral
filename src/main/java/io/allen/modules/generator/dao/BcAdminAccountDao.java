package io.allen.modules.generator.dao;

import io.allen.modules.generator.entity.BcAdminAccountEntity;
import io.allen.modules.sys.dao.BaseDao;

/**
 * 区块链账户
 * 
 * @author allen.liu
 * @date 2017-11-11 13:16:37
 */
public interface BcAdminAccountDao extends BaseDao<BcAdminAccountEntity> {
	/**
	 * @param userId
	 * @return
	 */
	BcAdminAccountEntity queryByUserId(Long userId);
}
