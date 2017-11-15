package io.allen.modules.generator.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

import io.allen.modules.generator.dao.BizExchangeRecordDao;
import io.allen.modules.generator.dao.BizGiftDao;
import io.allen.modules.generator.entity.BizExchangeRecordEntity;
import io.allen.modules.generator.service.BizExchangeRecordService;



@Service("bizExchangeRecordService")
public class BizExchangeRecordServiceImpl implements BizExchangeRecordService {
	@Autowired
	private BizExchangeRecordDao bizExchangeRecordDao;
	
	@Autowired
	private BizGiftDao bizGiftDao;
	
	@Override
	public BizExchangeRecordEntity queryObject(Long id){
		return bizExchangeRecordDao.queryObject(id);
	}
	
	@Override
	public List<BizExchangeRecordEntity> queryList(Map<String, Object> map){
		return bizExchangeRecordDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return bizExchangeRecordDao.queryTotal(map);
	}
	
	@Override
	@Transactional
	public void save(BizExchangeRecordEntity bizExchangeRecord){
		bizExchangeRecord.setCreateTime(new Date());
		bizExchangeRecordDao.save(bizExchangeRecord);
	}
	
	@Override
	@Transactional
	public void update(BizExchangeRecordEntity bizExchangeRecord){
		bizExchangeRecordDao.update(bizExchangeRecord);
	}
	
	@Override
	public void delete(Long id){
		bizExchangeRecordDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		bizExchangeRecordDao.deleteBatch(ids);
	}

	@Override
	public List<BizExchangeRecordEntity> queryPersonalList(Map<String, Object> map, Long userId) {
		map.put("userId", userId);
		return bizExchangeRecordDao.queryPersonalList(map);
	}

	@Override
	public int queryPersonalTotal(Map<String, Object> map, Long userId) {
		map.put("userId", userId);
		return bizExchangeRecordDao.queryPersonalTotal(map);
	}

	@Override
	@Transactional
	public void exchangeMinStock(BizExchangeRecordEntity bizExchangeRecord, Long giftId, Integer count) {
		bizExchangeRecord.setCreateTime(new Date());
		bizExchangeRecordDao.save(bizExchangeRecord);
		bizGiftDao.updateTotal(giftId, count);
	}
	
}
