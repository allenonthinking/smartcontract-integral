package io.allen.modules.generator.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.allen.modules.generator.dao.BcUserAdminDao;
import io.allen.modules.generator.entity.BcUserAdminEntity;
import io.allen.modules.generator.service.BcUserAdminService;



@Service("bcUserAdminService")
public class BcUserAdminServiceImpl implements BcUserAdminService {
	@Autowired
	private BcUserAdminDao bcUserAdminDao;
	
	@Override
	public BcUserAdminEntity queryObject(Long id){
		return bcUserAdminDao.queryObject(id);
	}
	
	@Override
	public List<BcUserAdminEntity> queryList(Map<String, Object> map){
		return bcUserAdminDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return bcUserAdminDao.queryTotal(map);
	}
	
	@Override
	public void save(BcUserAdminEntity bcUserAdmin){
		bcUserAdminDao.save(bcUserAdmin);
	}
	
	@Override
	public void update(BcUserAdminEntity bcUserAdmin){
		bcUserAdminDao.update(bcUserAdmin);
	}
	
	@Override
	public void delete(Long id){
		bcUserAdminDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		bcUserAdminDao.deleteBatch(ids);
	}

	@Override
	@Transactional
	public void bindingAdminAccount(long  accountId, long userId) {
		bcUserAdminDao.delete(accountId);
		BcUserAdminEntity userAdmin = new BcUserAdminEntity();
		userAdmin.setUserId(userId);
		userAdmin.setAccountId(accountId);
		bcUserAdminDao.save(userAdmin);
		
	}
	
}
