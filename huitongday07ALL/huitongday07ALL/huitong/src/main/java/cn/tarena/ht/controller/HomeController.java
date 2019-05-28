package cn.tarena.ht.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	//转向欢迎页面
	@RequestMapping("/home")
	public String home(){
		return "/home/fmain";
	}
	
	//转向tilte标题栏页面
	@RequestMapping("/title")
	public String title(){
		return "/home/title";
	}
	
	/*//转向home的左侧页面
	@RequestMapping("/homeLeft")
	public String homeLeft(){
		return "/home/left";
	}
	
	//转向home的操作页面
	@RequestMapping("/homeMain")
	public String homeMain(){
		return "/home/main";
	}
	
	//转向cargo页面：左菜单栏
	//cargoLeft : 请求是由 前端页面  moduleName + "Left.action"
	@RequestMapping(value="/cargoLeft")
	public String cargoLeft() {
		//货运管理的左菜单栏页面
		return "/cargo/left";
	}
	
	//货运管理的右界面
	@RequestMapping(value="/cargoMain")
	public String cargoMain() {
		//货运管理的右界面
		return "/cargo/main";
	}
	
	//转向baseinfo页面：左菜单栏
	@RequestMapping(value="/baseinfoLeft")
	public String baseinfoLeft() {
		//基础信息模块的左菜单栏页面
		return "/baseinfo/left";
	}
	
	//baseinfo的右界面
	@RequestMapping(value="/baseinfoMain")
	public String baseinfoMain() {
		//货运管理的右界面
		return "/baseinfo/main";
	}
	
	
	@RequestMapping("/sysadminLeft")
	public String sysadminLeft(){
		return "/sysadmin/left";
	}
	
	@RequestMapping("/sysadminMain")
	public String sysadminMain(){
		return "/sysadmin/main";
	}*/
	
	//使用RestFul的风格 定义请求的方法
	//对应着前端页面： moduleName+'/Left.action'
	//   /home/Left.action   /baseinfo/Left.action
	@RequestMapping(value="/{moduleName}/Left.action")
	public String moduelLeft(@PathVariable String moduleName) {
		return moduleName+"/left"; // home/left
	}
	
	@RequestMapping(value="/{moduleName}/Main.action")
	public String moduleMain(@PathVariable String moduleName) {
		return moduleName+"/main"; //home/main
	}
	
}
