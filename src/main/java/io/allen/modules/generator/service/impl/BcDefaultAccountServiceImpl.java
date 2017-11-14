package io.allen.modules.generator.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import io.allen.modules.generator.dao.BcDefaultAccountDao;
import io.allen.modules.generator.entity.BcDefaultAccountEntity;
import io.allen.modules.generator.service.BcDefaultAccountService;



@Service("bcDefaultAccountService")
public class BcDefaultAccountServiceImpl implements BcDefaultAccountService {
	@Autowired
	private BcDefaultAccountDao bcDefaultAccountDao;
	
	@Override
	public BcDefaultAccountEntity queryObject(Long accountId){
		return bcDefaultAccountDao.queryObject(accountId);
	}
	
	@Override
	public List<BcDefaultAccountEntity> queryList(Map<String, Object> map){
		return bcDefaultAccountDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return bcDefaultAccountDao.queryTotal(map);
	}
	
	@Override
	@Transactional
	public void save(BcDefaultAccountEntity bcDefaultAccount){
		bcDefaultAccountDao.save(bcDefaultAccount);
	}
	
	@Override
	@Transactional
	public void update(BcDefaultAccountEntity bcDefaultAccount){
		bcDefaultAccountDao.update(bcDefaultAccount);
	}
	
	@Override
	public void delete(Long accountId){
		bcDefaultAccountDao.delete(accountId);
	}
	
	@Override
	public void deleteBatch(Long[] accountIds){
		bcDefaultAccountDao.deleteBatch(accountIds);
	}

	@Override
	public List<BcDefaultAccountEntity> queryAccountByStatusAndType(Map<String, Object> map) {
		return bcDefaultAccountDao.queryAccountByStatusAndType(map);
	}
	
}
