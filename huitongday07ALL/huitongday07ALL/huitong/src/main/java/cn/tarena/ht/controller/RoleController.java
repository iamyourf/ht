package cn.tarena.ht.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.tarena.ht.pojo.Module;
import cn.tarena.ht.pojo.Role;
import cn.tarena.ht.service.ModuleService;
import cn.tarena.ht.service.RoleService;

@Controller
@RequestMapping(value="/sysadmin/role/")
public class RoleController {
	@Autowired
	private RoleService roleService;
	@Autowired
	private ModuleService moduleService;
	
	@RequestMapping(value="list")
	public String toRoleList(Model model) {
		//查询全部的角色信息
		List<Role> roleList = roleService.findAll();
		model.addAttribute("roleList", roleList);
		return "/sysadmin/role/jRoleList";//跳转到页面
	}
	
	//点击新增按钮，页面的跳转
	@RequestMapping(value="tosave")
	public String toSaveRole() {
		return "/sysadmin/role/jRoleSave";
	}
	
	//点击保存按钮，进行数据的保存
	@RequestMapping(value="save")
	public String saveRole(Role role) {
		roleService.saveRole(role);
		return "redirect:/sysadmin/role/list";
	}
	
	//点击修改按钮，页面的跳转
	@RequestMapping(value="toupdate")
	public String toUpdateRole(String roleId,Model model) {
		//准备工作：根据id去查询 数据库中的信息
		Role role = roleService.findRoleById(roleId);
		model.addAttribute("role", role);
		return "/sysadmin/role/jRoleUpdate";
	}
	
	//点击更新按钮，更新数据库
	@RequestMapping(value="update")
	public String updateRole (Role role) {
		//更新数据
		roleService.updateRole(role);
		return "redirect:/sysadmin/role/list";
	}
	
	//点击删除按钮，批量删除数据
	@RequestMapping(value="delete")
	public String deleteRole(
			@RequestParam(value="roleId",defaultValue="00") String[] roleIds) {
		//调用service层的方法
		roleService.deleteRole(roleIds);
		return "redirect:/sysadmin/role/list";
	}
	
	//点击查询按钮，页面的跳转
	@RequestMapping(value="toview")
	public String toViewRole(String roleId,Model model) {
		//准备页面显示的数据
		Role role = roleService.findRoleById(roleId);
		model.addAttribute("role", role);
		return "/sysadmin/role/jRoleView";
	}
	
	//点击模块按钮，页面的跳转：角色模块分配页面
	@RequestMapping(value="tomodule")
	public String toRoleModule(String roleId,Model model) throws JsonProcessingException {
		//根据roleId，准备已经勾选的模块的信息 findRoleModuleByRoleId
		List<String> checkedModule = moduleService.findRoleModuleByRoleId(roleId);
		
		//准备所有的模块信息的数据 
		List<Module> moduleList = moduleService.findAll();
		
		//数据的回显
		for (Module module : moduleList) {
			if (checkedModule.contains(module.getModuleId())) {
				module.setChecked(true);
			}
		}
		
		//把list集合的数据转换成 json格式的数据
		/* zTreeJson对象：id/pId/checked
		 */
		//使用ObjectMapper类进行格式的转换
		ObjectMapper objectMapper = new ObjectMapper();
		String zTreeJson = objectMapper.writeValueAsString(moduleList);
		//数据填充
		model.addAttribute("zTreeJson", zTreeJson);
		model.addAttribute("roleId", roleId);
		return "/sysadmin/role/jRoleModule";
	}
	
	@RequestMapping(value="saveRoleModule")
	public String saveRoleModule(String roleId,String moduleIds) {
		
		//插入中间表 role_module_p ： service层循环遍历的插入
		
		roleService.saveRoleModule(roleId, moduleIds);
		
		return "redirect:/sysadmin/role/list";
	}
	
	
}
