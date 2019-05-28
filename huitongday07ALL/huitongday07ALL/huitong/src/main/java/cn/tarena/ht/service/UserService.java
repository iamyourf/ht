package cn.tarena.ht.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tarena.ht.pojo.User;
import cn.tarena.ht.tool.PageBean;

public interface UserService {
	//查询全部的信息
	public List<User> findAll();
	//查询用户的总数据量
	public int getUserTotalCount();
	//根据分页，查询当前分页的数据
	public PageBean<User> findUserByPages(int currentPage,int pageSize);
	//查询上级领导的信息
	public List<User> findManagerUser();
	//新增操作
	public void saveUser(User user) ;
	//保存信息到中间表中
	public void saveUserRole(String userId,String roleIds);
	//根据userid查询中间表中的信息
	public List<String> findUserRoleByUserId(String userId);
	//登录时，根据用户名和密码查询是否存在此人
	public User checkLoginByUser(String userName,String password);
	//shiro的登录验证
	public User findUserByUsername(String username);
	//shiro的权限认证
	public List<String> findModuleNameByUserId(String userId);
}
