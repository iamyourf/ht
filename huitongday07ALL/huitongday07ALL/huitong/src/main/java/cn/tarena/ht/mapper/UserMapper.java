package cn.tarena.ht.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tarena.ht.pojo.User;
import cn.tarena.ht.pojo.UserInfo;

public interface UserMapper {
	//查询全部的信息
	public List<User> findAll();
	//查询用户的总数据量
	public int getUserTotalCount();
	//根据分页，查询当前分页的数据
	public List<User> findUserByPages(
			@Param(value="startRow") int startRow,
			@Param(value="pageSize") int pageSize);
	//查询上级领导的信息
	public List<User> findManagerUser();
	//保存 到 user_p表中
	public void saveUser(User user);
	//保存 到 user_info_p表中
	public void saveUserInfo(UserInfo userInfo);
	//保存到 role_user_p 表中n 
	public void saveUserRole(
			@Param(value="userId") String userId , 
			@Param(value="roleId") String roleId );
	//根据userid删除中间表中的信息
	public void deleteUserRole(String userId);
	//根据userid查询中间表中的信息
	public List<String> findUserRoleByUserId(String userId);
	
	//登录时，根据用户名和密码查询是否存在此人
	public User checkLoginByUser(
			@Param(value="userName") String userName,
			@Param(value="password") String password);
	//shiro的登录验证
	public User findUserByUsername(String username);
	
	//shiro的权限认证
	public List<String> findModuleNameByUserId(String userId);
}
