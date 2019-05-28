package cn.tarena.ht.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tarena.ht.pojo.Dept;
import cn.tarena.ht.service.DeptService;
import net.sf.json.JSONObject;

@Controller
public class DeptController {
	@Autowired
	private DeptService deptService;
	
	//列表页面的跳转
	/*@RequestMapping(value="/sysadmin/dept/list")
	public String toDeptList() {
		return "/sysadmin/dept/jDeptList";//返回的页面路径和名称
	}*/
	
	//根据实际的业务逻辑，在页面跳转时，先查询所有的列表信息
	@RequestMapping(value="/sysadmin/dept/list")
	public String toDeptList(Model model) {
		//调用service层的findAll方法
		List<Dept> deptList = deptService.findAll();
		//数据填充
		model.addAttribute("deptList", deptList);
		return "/sysadmin/dept/jDeptList";//返回的页面路径和名称
	}
	
	//部门状态的停用
	@RequestMapping(value="/sysadmin/dept/stop")
	public String toStop(
			@RequestParam(value="deptId",defaultValue="0") String[] deptIds) {
		//停用的状态
		int state = 0;
		//调用业务层的方法
		deptService.updateState(deptIds, state);
		//重定向了请求：list请求
		//这时，相当于重新执行了一遍 toDeptList方法
		return "redirect:/sysadmin/dept/list";
	}
	
	//部门状态的启动
	@RequestMapping(value="/sysadmin/dept/start")
	public String toStart(
			@RequestParam(value="deptId",defaultValue="0") String[] deptIds) {
		//启动的状态
		int state = 1;
		//调用业务层的方法
		deptService.updateState(deptIds, state);
		//重定向了请求：list请求
		//这时，相当于重新执行了一遍 toDeptList方法
		return "redirect:/sysadmin/dept/list";
	}
	
	//批量删除
	@RequestMapping(value="/sysadmin/dept/delete")
	public String deleteDept(
			@RequestParam(value="deptId",defaultValue="0") String[] deptIds) {
		
		deptService.deleteDept(deptIds);
		
		//重定向了请求：list请求
		//这时，相当于重新执行了一遍 toDeptList方法
		return "redirect:/sysadmin/dept/list";
	}
	
	//点击新增按钮，实现页面跳转
	@RequestMapping(value="/sysadmin/dept/tosave")
	public String toSaveDept(Model model) {
		//准备 上级部门信息的数据
		List<Dept> parentDeptList = deptService.
				findParentDept();
		for (Dept dept : parentDeptList) {
			System.out.println("==========="+dept);
		}
		//数据填充
		model.addAttribute("parentDeptList", 
				parentDeptList);
		return "/sysadmin/dept/jDeptSave";
	}
	
	//新增页面的保存方法
	@RequestMapping(value="/sysadmin/dept/save")
	public String saveDept(Dept dept , Model model) {
		//在执行插入方法之前，先教研数据是否存在
		System.out.println("************"+dept);
		String deptId = dept.getDeptId();
		if (deptId == null || "".equals(deptId)) {
			return "forward:/sysadmin/dept/tosave";
		}
		Dept checkDept = deptService.findDeptById(deptId);
		
		if (checkDept != null) {//表示  存在主键冲突
			//给出前端页面一个错误信息
			model.addAttribute("errorInfo", "该部门编号已存在");
			//使用转发：主要作用：把错误信息带入到页面中
			return "forward:/sysadmin/dept/tosave";
		}
		deptService.saveDept(dept);
		//重定向了请求：list请求
		//这时，相当于重新执行了一遍 toDeptList方法
		return "redirect:/sysadmin/dept/list";
		//重定向页面时，无法解析路径与页面
//		return "redirect:/sysadmin/dept/jDeptList.jsp";
	}
	
	//点击修改按钮，实现页面的跳转
	@RequestMapping(value="/sysadmin/dept/toupdate")
	public String toUpdateDept(String deptId,Model model) {
		//数据的回显：需要自关联的查询
		Dept dept = deptService.findDeptBackById(deptId);
		//准备 上级部门的信息
		//准备 上级部门信息的数据
		List<Dept> parentDeptList = deptService.
				findParentDept();
		for (Dept dept1 : parentDeptList) {
			System.out.println("==========="+dept1);
		}
		//数据填充
		model.addAttribute("dept", dept);
		model.addAttribute("parentDeptList", parentDeptList);
		return "/sysadmin/dept/jDeptUpdate";
	}
	
	//点击更新页面，实现数据的更新
	@RequestMapping(value="/sysadmin/dept/update")
	public String updateDept(Dept dept,Model model) {
		//验证当前维护的数据，id 与 name 是否 存在冲突
		//在执行插入方法之前，先教研数据是否存在
		System.out.println("************"+dept);
		String deptId = dept.getDeptId();
		if (deptId == null || "".equals(deptId)) {
			return "forward:/sysadmin/dept/tosave";
		}
		Dept checkDept = deptService.findDeptById(deptId);
		
		if (checkDept != null) {//表示  存在主键冲突
			//给出前端页面一个错误信息
			model.addAttribute("errorInfo", "该部门编号已存在");
			//使用转发：主要作用：把错误信息带入到页面中
			return "forward:/sysadmin/dept/tosave";
		}
		return "redirect:/sysadmin/dept/list";
	}
	
	//ajax异步校验 deptId是否存在
	//  sysadmin/dept/checkDeptId
	//  data : {deptId的key : deptId的值}
	// 返回值使用json格式
	@RequestMapping(value="/sysadmin/dept/checkDeptId")
	@ResponseBody  //把json对象放入到response中
	public JSONObject checkDeptId(String deptId){
		//创建一个json对象，用于结果的封装
		JSONObject jsonObject = new JSONObject();
		System.out.println("**********"+deptId);
		//根据deptId查询数据库是否有部门信息
		Dept dept = deptService.findDeptById(deptId);
		//判断部门信息是否为空
		if (dept == null) { //部门不存在，可以使用
			jsonObject.put("result", "false");
		} else {
			jsonObject.put("result", "true");
		}
		return jsonObject;//返回一个对象，该对象放在了response中
	}
	
	
	
	/*@RequestMapping(value="/sysadmin/dept/checkDeptId")
	@ResponseBody
	public JSONObject checkDeptId(String deptId) {
		JSONObject jsonObject = new JSONObject();
		System.out.println("**********"+deptId);
		Dept dept = deptService.findDeptById(deptId);
		if (dept == null) {
			jsonObject.put("result", "true");
		} else {
			jsonObject.put("result", "false");
		}
		return jsonObject;
	}*/
	
}
