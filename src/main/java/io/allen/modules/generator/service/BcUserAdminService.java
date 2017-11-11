package io.allen.modules.generator.service;

import io.allen.modules.generator.entity.BcUserAdminEntity;
import io.allen.modules.integral.entity.IntegralEntity;
import io.allen.modules.sys.entity.SysUserEntity;

import java.util.List;
import java.util.Map;

/**
 * 管理员用户与区块链账户对应关系
 * 
 * @author allen.liu
 * @date 2017-11-11 13:16:36
 */
public interface BcUserAdminService {
	
	BcUserAdminEntity queryObject(Long id);
	
	List<BcUserAdminEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(BcUserAdminEntity bcUserAdmin);
	
	void update(BcUserAdminEntity bcUserAdmin);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
	
	void bindingAdminAccount(long accountId, long  userId);
	
}
