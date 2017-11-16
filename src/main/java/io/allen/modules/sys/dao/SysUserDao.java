package io.allen.modules.sys.dao;

import java.util.List;
import java.util.Map;

import io.allen.modules.sys.entity.SysUserEntity;

/**
 * 系统用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:34:11
 */
public interface SysUserDao extends BaseDao<SysUserEntity> {
	
	/**
	 * 查询用户的所有权限
	 * @param userId  用户ID
	 */
	List<String> queryAllPerms(Long userId);
	
	/**
	 * 查询用户的所有菜单ID
	 */
	List<Long> queryAllMenuId(Long userId);
	
	/**
	 * 根据用户名，查询系统用户
	 */
	SysUserEntity queryByUserName(String username);
	
	/**
	 * 修改密码
	 */
	int updatePassword(Map<String, Object> map);
	
	/**
	 *查询管理用户列表 
	 */
	List<SysUserEntity> querySystemList(Map<String, Object> map);
	
	/**
	 * 查询管理用户总数
	 */
	int querySystemTotal(Map<String, Object> map);
	
	/**
	 *查询普通用户列表 
	 */
	List<SysUserEntity> queryNormalList(Map<String, Object> map);
	
	/**
	 * 查询普通用户总数
	 */
	int queryNormalTotal(Map<String, Object> map);
	
	/**
	 *查询积分用户列表 
	 */
	List<SysUserEntity> queryIntegralAccountList(Map<String, Object> map);
	
	/**
	 * 查询积分用户总数
	 */
	int queryIntegralAccountTotal(Map<String, Object> map);
	
	
	
	
}
