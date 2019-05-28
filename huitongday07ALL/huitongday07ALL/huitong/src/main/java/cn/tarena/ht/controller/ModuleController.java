package cn.tarena.ht.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.tarena.ht.pojo.Module;
import cn.tarena.ht.service.ModuleService;

@Controller
@RequestMapping(value="/sysadmin/module/")
public class ModuleController {
	@Autowired
	private ModuleService moduleService;
	//页面的跳转
	@RequestMapping(value="list")
	public String toModuleList(Model model) {
		List<Module> moduleList = moduleService.findAll();
		model.addAttribute("moduleList", moduleList);
		return "/sysadmin/module/jModuleList";
	}
	
	//点击新增按钮，跳转新增页面
	@RequestMapping(value="tosave")
	public String toSaveModule(Model model) {
		//准备上级模块的信息 findParentModule()
		List<Module> parentModuleList = moduleService.findParentModule();
		model.addAttribute("parentModuleList", parentModuleList);
		return "/sysadmin/module/jModuleSave";
	}
	
	//点击保存按钮，进行数据的新增
	@RequestMapping(value="save")
	public String saveModule(Module module) {
		//调用service层的新增方法
		moduleService.saveModule(module);
		return "redirect:/sysadmin/module/list";
	}
}
