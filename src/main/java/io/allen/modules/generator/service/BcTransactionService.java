package io.allen.modules.generator.service;

import io.allen.modules.generator.entity.BcTransactionEntity;

import java.util.List;
import java.util.Map;

/**
 * 交易表
 * 
 * @author allen.liu
 * @date 2017-11-13 11:24:46
 */
public interface BcTransactionService {
	
	BcTransactionEntity queryObject(Long id);
	
	List<BcTransactionEntity> queryList(Map<String, Object> map);
	
	List<BcTransactionEntity> queryNotProcessed(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(BcTransactionEntity bcTransaction);
	
	void update(BcTransactionEntity bcTransaction);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
	
	void updateStatus(BcTransactionEntity bcTransaction);
}
