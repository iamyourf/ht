package cn.tarena.ht.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.tarena.ht.pojo.Dept;
import cn.tarena.ht.pojo.Role;
import cn.tarena.ht.pojo.User;
import cn.tarena.ht.service.DeptService;
import cn.tarena.ht.service.RoleService;
import cn.tarena.ht.service.UserService;
import cn.tarena.ht.tool.PageBean;

@Controller
@RequestMapping(value="/sysadmin/user") //请求的公共部分
public class UserController extends BaseController{
	@Autowired
	private UserService userService;
	@Autowired
	private DeptService deptService;
	@Autowired
	private RoleService roleService;
	//用户列表页面的跳转
	/*@RequestMapping(value="/list") //每个请求的action
	public String toUserList(Model model) {
		//查询全部的用户信息
		List<User> userList = userService.findAll();
		//数据填充
		model.addAttribute("userList", userList);
		return "/sysadmin/user/jUserList";
	}*/
	@RequestMapping(value="/list") //每个请求的action
	public String toUserList(
			@RequestParam(defaultValue="1") int currentPage, 
			@RequestParam(defaultValue="10") int pageSize,Model model) {
		//查询分页的数据：默认查询，每一页为10条数据，从第0条开始
		System.out.println("************"+currentPage);
		System.out.println("------------"+pageSize);
		PageBean<User> pageBean = userService.findUserByPages(currentPage, pageSize);
		List<User> userList = pageBean.getPageList();
		//数据填充
		model.addAttribute("userList", userList);
		model.addAttribute("pageBean", pageBean);
		return "/sysadmin/user/jUserList";
	}
	
	//点击新增按钮，跳转页面
	@RequestMapping(value="/tosave")
	public String toSaveUser(Model model) {
		//准备 上级领导的信息
		List<User> userInfoList = userService.findManagerUser();
		//准备 所属部门的信息
		List<Dept> deptList = deptService.findAll();
		for (Dept dept : deptList) {
			System.out.println("==========="+dept);
		}
		//数据填充
		model.addAttribute("userInfoList", userInfoList);
		model.addAttribute("deptList", deptList);
		return "/sysadmin/user/jUserSave";
	}
	
	//点击保存按钮，实现保存功能
	@RequestMapping(value="/save")
	public String saveUser(User user,Model model) {
		//使用ajax检验用户的用户名是否存在冲突
		
		//调用service层的保存方法
		userService.saveUser(user);
		
		//重定向
		return "redirect:/sysadmin/user/list";
	}
	
	//点击角色按钮，实现页面的跳转
	@RequestMapping(value="torole")
	public String toUserRole(String userId,Model model) throws JsonProcessingException {
		//查询真是的全部的角色信息
		List<Role> roleList = roleService.findAll();
		//根据userid查询出所有的已经勾选的信息
		List<String> userRoles = userService.findUserRoleByUserId(userId);
		//循环遍历所有的角色
		for (Role role : roleList) {//循环全部角色
			for (String roleId : userRoles) {//循环勾选信息
				if (roleId.equals(role.getRoleId())) {//勾选的id 与 所有角色匹配
					//对roleList集合中的对象，添加checked属性
					role.setChecked(true);
				}
			}
		}
		//使用ObjectMapper类 进行转换为json格式
		ObjectMapper objectMapper = new ObjectMapper();
		//对集合中的数据，对象中的每个值是通过get方式进行底层的转换
		String zTreeJson = objectMapper.writeValueAsString(roleList);
		System.out.println("*************"+zTreeJson);
		model.addAttribute("zTreeJson", zTreeJson);
		model.addAttribute("userId", userId);
		return "/sysadmin/user/jUserRole";
	}
	
	//点击保存按钮，保存勾选的角色信息
	//该数据是保存到  role_user_p 的中间表中
	@RequestMapping(value="saveUserRole")
	public String saveRole(String userId,String roleIds) {
		userService.saveUserRole(userId, roleIds);
		return "redirect:/sysadmin/user/list";
	} 
	
}
