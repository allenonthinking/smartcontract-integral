package io.allen.modules.generator.service;

import io.allen.modules.generator.entity.BcAdminAccountEntity;
import io.allen.modules.integral.entity.IntegralEntity;

import java.util.List;
import java.util.Map;

/**
 * 区块链账户
 * 
 * @author allen.liu
 * @date 2017-11-11 13:16:37
 */
public interface BcAdminAccountService {
	
	BcAdminAccountEntity queryObject(Long accountId);
	
	List<BcAdminAccountEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(BcAdminAccountEntity bcAdminAccount);
	
	void save(BcAdminAccountEntity bcAdminAccount,long userId);
	
	void update(BcAdminAccountEntity bcAdminAccount);
	
	void delete(Long accountId);
	
	void deleteBatch(Long[] accountIds);
	
	BcAdminAccountEntity queryByUserId(Long userId);
}
