package io.allen.modules.generator.service;

import io.allen.modules.generator.entity.BcDefaultAccountEntity;

import java.util.List;
import java.util.Map;

/**
 * 默认区块链账户
 * 
 * @author allen.liu
 * @date 2017-11-14 09:36:52
 */
public interface BcDefaultAccountService {
	
	BcDefaultAccountEntity queryObject(Long accountId);
	
	List<BcDefaultAccountEntity> queryList(Map<String, Object> map);
	
	List<BcDefaultAccountEntity> queryAccountByStatusAndType(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(BcDefaultAccountEntity bcDefaultAccount);
	
	void update(BcDefaultAccountEntity bcDefaultAccount);
	
	void delete(Long accountId);
	
	void deleteBatch(Long[] accountIds);
}
