package cn.tarena.ht.service;

import java.util.List;

import cn.tarena.ht.pojo.Module;

public interface ModuleService {
	public List<Module> findAll();
	//准备所有的上级模块的数据
	public List<Module> findParentModule();
	//数据的新增
	public void saveModule(Module module);
	
	//查询已经勾选的模块的信息
	public List<String> findRoleModuleByRoleId(String roleId);
}
