package io.allen.modules.sys.service;


import java.util.List;
import java.util.Map;

import io.allen.modules.sys.entity.SysRoleEntity;


/**
 * 角色
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:42:52
 */
public interface SysRoleService {
	
	SysRoleEntity queryObject(Long roleId);
	
	List<SysRoleEntity> queryList(Map<String, Object> map);
	
	List<SysRoleEntity> queryNormalList(Map<String, Object> map);
	
	List<SysRoleEntity> querySystemList(Map<String, Object> map);
	
	SysRoleEntity queryByUserId(Long userId);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysRoleEntity role);
	
	void update(SysRoleEntity role);
	
	void deleteBatch(Long[] roleIds);

}
