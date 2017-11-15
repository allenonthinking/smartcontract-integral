package io.allen.modules.generator.dao;

import java.util.List;
import java.util.Map;

import io.allen.modules.generator.entity.BizGiftEntity;
import io.allen.modules.sys.dao.BaseDao;

/**
 * 礼品表
 * 
 * @author allen.liu
 * @date 2017-11-15 09:25:12
 */
public interface BizGiftDao extends BaseDao<BizGiftEntity> {
	
	List<BizGiftEntity> queryExchangeList(Map<String, Object> map);
	
	int queryExchangeTotal(Map<String, Object> map);
}
