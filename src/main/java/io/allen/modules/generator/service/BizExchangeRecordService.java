package io.allen.modules.generator.service;

import io.allen.modules.generator.entity.BizExchangeRecordEntity;

import java.util.List;
import java.util.Map;

/**
 * 礼品兑换记录
 * 
 * @author allen.liu
 * @date 2017-11-15 10:55:46
 */
public interface BizExchangeRecordService {
	
	BizExchangeRecordEntity queryObject(Long id);
	
	List<BizExchangeRecordEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	List<BizExchangeRecordEntity> queryPersonalList(Map<String, Object> map,Long userId);
	
	int queryPersonalTotal(Map<String, Object> map,Long userId);
	
	void save(BizExchangeRecordEntity bizExchangeRecord);
	
	void update(BizExchangeRecordEntity bizExchangeRecord);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
	
	void exchangeMinStock(BizExchangeRecordEntity bizExchangeRecord,Long giftId, Integer count);
}
