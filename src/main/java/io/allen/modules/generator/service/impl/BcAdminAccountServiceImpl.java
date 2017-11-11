package io.allen.modules.generator.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

import io.allen.modules.generator.dao.BcAdminAccountDao;
import io.allen.modules.generator.dao.BcUserAdminDao;
import io.allen.modules.generator.entity.BcAdminAccountEntity;
import io.allen.modules.generator.service.BcAdminAccountService;
import io.allen.modules.generator.service.BcUserAdminService;



@Service("bcAdminAccountService")
public class BcAdminAccountServiceImpl implements BcAdminAccountService {
	
	@Autowired
	private BcAdminAccountDao bcAdminAccountDao;

	@Autowired
	private BcUserAdminService bcUserAdminService;
	
	@Override
	public BcAdminAccountEntity queryObject(Long accountId){
		return bcAdminAccountDao.queryObject(accountId);
	}
	
	@Override
	public List<BcAdminAccountEntity> queryList(Map<String, Object> map){
		return bcAdminAccountDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return bcAdminAccountDao.queryTotal(map);
	}
	
	@Override
	public void save(BcAdminAccountEntity bcAdminAccount){
		bcAdminAccountDao.save(bcAdminAccount);
	}
	
	@Override
	public void update(BcAdminAccountEntity bcAdminAccount){
		bcAdminAccountDao.update(bcAdminAccount);
	}
	
	@Override
	public void delete(Long accountId){
		bcAdminAccountDao.delete(accountId);
	}
	
	@Override
	public void deleteBatch(Long[] accountIds){
		bcAdminAccountDao.deleteBatch(accountIds);
	}

	@Override
	public BcAdminAccountEntity queryByUserId(Long userId) {
		return bcAdminAccountDao.queryByUserId(userId);
	}

	@Override
	@Transactional
	public void save(BcAdminAccountEntity bcAdminAccount, long userId) {
		
		bcAdminAccount.setCreateTime(new Date());
		bcAdminAccountDao.save(bcAdminAccount);
		bcUserAdminService.bindingAdminAccount(bcAdminAccount.getAccountId(), userId);
		
	}
	
}
