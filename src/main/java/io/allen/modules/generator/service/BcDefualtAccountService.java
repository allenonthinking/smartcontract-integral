package io.allen.modules.generator.service;

import io.allen.modules.generator.entity.BcDefualtAccountEntity;

import java.util.List;
import java.util.Map;

/**
 * 默认区块链账户
 * 
 * @author allen.liu
 * @date 2017-11-14 09:36:52
 */
public interface BcDefualtAccountService {
	
	BcDefualtAccountEntity queryObject(Long accountId);
	
	List<BcDefualtAccountEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(BcDefualtAccountEntity bcDefualtAccount);
	
	void update(BcDefualtAccountEntity bcDefualtAccount);
	
	void delete(Long accountId);
	
	void deleteBatch(Long[] accountIds);
}
