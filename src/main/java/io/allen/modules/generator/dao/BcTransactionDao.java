package io.allen.modules.generator.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import io.allen.modules.generator.entity.BcTransactionEntity;
import io.allen.modules.sys.dao.BaseDao;

/**
 * 交易表
 * 
 * @author allen.liu
 * @date 2017-11-13 11:24:46
 */
@Mapper
public interface BcTransactionDao extends BaseDao<BcTransactionEntity> {
	List<BcTransactionEntity> queryNotProcessed(Map<String, Object> map);
	
	int queryNotProcessedTotal(); 
	
	int updateStatus(BcTransactionEntity bcTransactionEntity);
}
