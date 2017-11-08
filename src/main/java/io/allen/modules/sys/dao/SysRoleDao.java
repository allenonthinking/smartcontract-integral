package io.allen.modules.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import io.allen.modules.sys.entity.SysRoleEntity;

/**
 * 角色管理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:33:33
 */
@Mapper
public interface SysRoleDao extends BaseDao<SysRoleEntity> {
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	List<SysRoleEntity> queryNormalList(Map<String, Object> map);
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	List<SysRoleEntity> querySystemList(Map<String, Object> map);
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	SysRoleEntity queryByUserId(Long userId);
	
}
