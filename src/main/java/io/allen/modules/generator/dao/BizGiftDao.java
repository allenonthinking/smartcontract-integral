package io.allen.modules.generator.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import io.allen.modules.generator.entity.BizGiftEntity;
import io.allen.modules.sys.dao.BaseDao;

/**
 * 礼品表
 * 
 * @author allen.liu
 * @date 2017-11-15 09:25:12
 */
@Mapper
public interface BizGiftDao extends BaseDao<BizGiftEntity> {
	
	List<BizGiftEntity> queryExchangeList(Map<String, Object> map);
	
	int queryExchangeTotal(Map<String, Object> map);
	
	int updateTotal(@Param("giftId")Long giftId, @Param("count")Integer count);
}
