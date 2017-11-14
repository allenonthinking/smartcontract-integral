package io.allen.modules.generator.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import io.allen.modules.generator.dao.BcDefualtAccountDao;
import io.allen.modules.generator.entity.BcDefualtAccountEntity;
import io.allen.modules.generator.service.BcDefualtAccountService;



@Service("bcDefualtAccountService")
public class BcDefualtAccountServiceImpl implements BcDefualtAccountService {
	@Autowired
	private BcDefualtAccountDao bcDefualtAccountDao;
	
	@Override
	public BcDefualtAccountEntity queryObject(Long accountId){
		return bcDefualtAccountDao.queryObject(accountId);
	}
	
	@Override
	public List<BcDefualtAccountEntity> queryList(Map<String, Object> map){
		return bcDefualtAccountDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return bcDefualtAccountDao.queryTotal(map);
	}
	
	@Override
	@Transactional
	public void save(BcDefualtAccountEntity bcDefualtAccount){
		bcDefualtAccountDao.save(bcDefualtAccount);
	}
	
	@Override
	@Transactional
	public void update(BcDefualtAccountEntity bcDefualtAccount){
		bcDefualtAccountDao.update(bcDefualtAccount);
	}
	
	@Override
	public void delete(Long accountId){
		bcDefualtAccountDao.delete(accountId);
	}
	
	@Override
	public void deleteBatch(Long[] accountIds){
		bcDefualtAccountDao.deleteBatch(accountIds);
	}
	
}
