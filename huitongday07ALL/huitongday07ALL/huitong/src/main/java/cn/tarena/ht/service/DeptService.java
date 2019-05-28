package cn.tarena.ht.service;

import java.util.List;

import cn.tarena.ht.pojo.Dept;

public interface DeptService {
	public List<Dept> findAll();
	//状态的启动和停止
	public void updateState(String[] deptIds,int state);
	//批量删除
	public void deleteDept(String[] deptIds);
	//查询所有的上级部门
	public List<Dept> findParentDept();
	//插入一条部门信息
	public void saveDept(Dept dept);
	// 根据id查询部门信息
	public Dept findDeptById(String deptId);
	// 修改页面的数据回显
	public Dept findDeptBackById(String deptId);
}
