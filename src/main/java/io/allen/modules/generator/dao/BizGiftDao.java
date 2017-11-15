package io.allen.modules.generator.dao;

import org.apache.ibatis.annotations.Mapper;

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
	
}
