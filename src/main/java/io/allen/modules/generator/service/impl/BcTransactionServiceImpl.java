package io.allen.modules.generator.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import io.allen.modules.generator.dao.BcTransactionDao;
import io.allen.modules.generator.entity.BcTransactionEntity;
import io.allen.modules.generator.service.BcTransactionService;



@Service("bcTransactionService")
public class BcTransactionServiceImpl implements BcTransactionService {
	@Autowired
	private BcTransactionDao bcTransactionDao;
	
	@Override
	public BcTransactionEntity queryObject(Long id){
		return bcTransactionDao.queryObject(id);
	}
	
	@Override
	public List<BcTransactionEntity> queryList(Map<String, Object> map){
		return bcTransactionDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return bcTransactionDao.queryTotal(map);
	}
	
	@Override
	@Transactional
	public void save(BcTransactionEntity bcTransaction){
		bcTransactionDao.save(bcTransaction);
	}
	
	@Override
	public void update(BcTransactionEntity bcTransaction){
		bcTransactionDao.update(bcTransaction);
	}
	
	@Override
	public void delete(Long id){
		bcTransactionDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		bcTransactionDao.deleteBatch(ids);
	}
	
}
