package io.allen.modules.generator.dao;

import java.util.List;
import java.util.Map;

import io.allen.modules.generator.entity.BizExchangeRecordEntity;
import io.allen.modules.sys.dao.BaseDao;

/**
 * 礼品兑换记录
 * 
 * @author allen.liu
 * @date 2017-11-15 10:55:46
 */
public interface BizExchangeRecordDao extends BaseDao<BizExchangeRecordEntity> {
	List<BizExchangeRecordEntity> queryPersonalList(Map<String, Object> map);
	
	int queryPersonalTotal(Map<String, Object> map);
}
