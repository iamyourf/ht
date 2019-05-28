package cn.tarena.ht.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tarena.ht.mapper.ModuleMapper;
import cn.tarena.ht.pojo.Module;

@Service
public class ModuleServiceImpl implements ModuleService{
	@Autowired
	private ModuleMapper moduleMapper;
	
	@Override
	public List<Module> findAll() {
		return moduleMapper.findAll();
	}

	@Override
	public List<Module> findParentModule() {
		return moduleMapper.findParentModule();
	}

	@Override
	public void saveModule(Module module) {
		//自动生成主键
		String uuid = UUID.randomUUID().toString();
		module.setModuleId(uuid);
		moduleMapper.saveModule(module);
	}

	@Override
	public List<String> findRoleModuleByRoleId(String roleId) {
		return moduleMapper.findRoleModuleByRoleId(roleId);
	}

}
