package io.allen.modules.generator.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.allen.modules.generator.dao.BizGiftDao;
import io.allen.modules.generator.entity.BizGiftEntity;
import io.allen.modules.generator.service.BizGiftService;



@Service("bizGiftService")
public class BizGiftServiceImpl implements BizGiftService {
	@Autowired
	private BizGiftDao bizGiftDao;
	
	@Override
	public BizGiftEntity queryObject(Long giftId){
		return bizGiftDao.queryObject(giftId);
	}
	
	@Override
	public List<BizGiftEntity> queryList(Map<String, Object> map){
		return bizGiftDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return bizGiftDao.queryTotal(map);
	}
	
	@Override
	@Transactional
	public void save(BizGiftEntity bizGift){
		bizGift.setCreateTime(new Date());
		bizGiftDao.save(bizGift);
	}
	
	@Override
	@Transactional
	public void update(BizGiftEntity bizGift){
		bizGiftDao.update(bizGift);
	}
	
	@Override
	public void delete(Long giftId){
		bizGiftDao.delete(giftId);
	}
	
	@Override
	public void deleteBatch(Long[] giftIds){
		bizGiftDao.deleteBatch(giftIds);
	}

	@Override
	public List<BizGiftEntity> queryExchangeList(Map<String, Object> map) {
		return bizGiftDao.queryExchangeList(map);
	}

	@Override
	public int queryExchangeTotal(Map<String, Object> map) {
		return bizGiftDao.queryExchangeTotal(map);
	}

	@Override
	@Transactional
	public void updateTotal(Long giftId, Integer count) {
		bizGiftDao.updateTotal(giftId, count);
	}
	
}
