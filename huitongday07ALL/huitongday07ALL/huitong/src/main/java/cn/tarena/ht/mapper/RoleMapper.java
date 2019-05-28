package cn.tarena.ht.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import cn.tarena.ht.pojo.Role;

public interface RoleMapper {
	
	//当 逆向去 生成 RoleMapper 的代理类时，会自动的进行注解的解析
	//在解析到相应的 增删改查的注解时，会自动的进行sql的执行和映射
	//使用注解进行sql语句的编写时，主要是用于单表的操作
	/*单表操作
	@Select
	@Insert
	@Update
	@Delete*/
	@Select(value="select * from role_p")
	public 	List<Role> findAll();
	
	@Insert(value="insert into role_p (role_id,name,remarks,order_no) values (#{roleId},#{name},#{remarks},#{orderNo}) ")
	public void saveRole(Role role);
	
	@Update(value="update role_p set name=#{name},remarks=#{remarks},order_no=#{orderNo} where role_id=#{roleId}")
	public void updateRole(Role role);
	
	@Select(value="select * from role_p where role_id=#{roleId}")
	public Role findRoleById(String roleId);
	
	public void deleteRole(String[] roleIds);
	
	//批量插入数据中间表
	public void saveRoleModule(
			@Param(value="roleId") String roleId,
			@Param(value="moduleIds") String[] moduleIds
			);
	
}
