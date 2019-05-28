package cn.tarena.ht.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tarena.ht.pojo.Dept;

public interface DeptMapper {
	public List<Dept> findAll();
	//状态的启动和停用
	//使用注解@Param的形式对map集合进行封装
	//key : value键值对
	public void updateState(
			@Param(value="deptIds") String[] deptIds,
			@Param(value="state") int state);
	//批量删除
	public void deleteDept(String[] deptIds) ;
	//查询所有的上级部门
	public List<Dept> findParentDept();
	//插入一条部门信息
	public void saveDept(Dept dept);
	// 根据id查询部门信息
	public Dept findDeptById(String deptId);
	// 修改页面的数据回显
	public Dept findDeptBackById(String deptId);
}
