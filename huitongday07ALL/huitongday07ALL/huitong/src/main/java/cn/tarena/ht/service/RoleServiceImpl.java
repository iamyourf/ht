package cn.tarena.ht.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tarena.ht.mapper.RoleMapper;
import cn.tarena.ht.pojo.Role;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleMapper roleMapper;
	
	@Override
	public List<Role> findAll() {
		return roleMapper.findAll();
	}

	@Override
	public void saveRole(Role role) {
		//自动生成主键
		String roleId = UUID.randomUUID().toString();
		role.setRoleId(roleId);
		roleMapper.saveRole(role);
	}

	@Override
	public Role findRoleById(String roleId) {
		return roleMapper.findRoleById(roleId);
	}

	@Override
	public void updateRole(Role role) {
		roleMapper.updateRole(role);
	}

	@Override
	public void deleteRole(String[] roleIds) {
		roleMapper.deleteRole(roleIds);
	}

	//批量插入
	@Override
	public void saveRoleModule(String roleId, String moduleIds) {
		String[] modules = moduleIds.split(",");
		roleMapper.saveRoleModule(roleId ,modules);
	}

}
