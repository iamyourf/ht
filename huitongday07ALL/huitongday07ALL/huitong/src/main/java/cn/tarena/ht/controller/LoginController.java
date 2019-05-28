package cn.tarena.ht.controller;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.tarena.ht.pojo.User;
import cn.tarena.ht.service.UserService;
import cn.tarena.ht.tool.Md5Password;

@Controller
public class LoginController {
	@Autowired
	private UserService userService;
	
	//点击登出按钮，页面的跳转
	@RequestMapping(value="/logout")
	public String toLoginOut(HttpSession session) {
		User user = (User) session.getAttribute("sessionUser");
		System.out.println("___________"+user);
		session.setAttribute("sessionUser", null);
		return "/sysadmin/login/login";
	}
	
	//使用shiro的安全管理进行 登录的认证
	@RequestMapping(value="/login")
	public String toLogin(String userName , String password,Model model,HttpSession session ) {
		//1/验证 用户名 和 密码 是否为空 : 空的字符串
		if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
			//验证结果:数据填充
			model.addAttribute("errorInfo", "用户名或者密码不能为空");
			//页面的跳转到 登录界面
			return "/sysadmin/login/login";
		}
		
		//2/验证 用户名 和 密码 是否正确
		// 使用的是 shiro提供的Subject类 : 获取当前登录的对象
		// subject对象 表示 所有请求对应的统一的对象
		Subject subject = SecurityUtils.getSubject() ;//创建subject对象，由shiro安全管理创建
        
		//参数：AuthenticationToken ： 令牌（票）
		//拿到当前登录的用户名和密码
		//令牌对象是由shiro提供的
		UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
		
		//shiro提供的登录的方法
		try { // 表示 正常执行的业务逻辑：
			subject.login(token);
			// 获取的用户放入到session当中
			//获取真实对象 ： 是从数据库中已经验证之后的用户
			User user = (User) subject.getPrincipal();
			//把对象添加到session中：shiro提供的session对象
			subject.getSession().setAttribute("sessionUser", user);
			//如果登录成功，直接进行页面的跳转
			return "redirect:/home.action";
		} catch (Exception e) { // 表示 令牌认证失败
			e.printStackTrace();
			//认证失败时，添加错误信息的描述，使用model对象填充
			//验证结果:数据填充
			model.addAttribute("errorInfo", "用户名或者密码不正确");
			//页面的跳转到 登录界面
			return "/sysadmin/login/login";
		}
		
		
		
		//3/ 把数据放入到session对象中
		
		
		
	}
	
	//点击登录按钮，页面的跳转
	/* 
	 * 登录成功：跳转到主界面
	 * 登录失败：跳转到当前页面，error信息显示在页面
	 */
	/*@RequestMapping(value="/login")
	public String toLogin(String userName , String password,Model model,HttpSession session ) {
		//1/验证 用户名 和 密码 是否为空 : 空的字符串
		if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
			//验证结果:数据填充
			model.addAttribute("errorInfo", "用户名或者密码不能为空");
			//页面的跳转到 登录界面
			return "/sysadmin/login/login";
		}
		
		//2/验证 用户名 和 密码 是否正确
		//查询数据库中是否有此人
		//同时需要查询此人的部门信息
		
		//使用md5算法对密码进行加密之后再验证
		String md5password = Md5Password.getMd5HashPassword(password, userName);
		
		User user = userService.checkLoginByUser(userName, md5password);
		if (StringUtils.isEmpty(user) || user == null ) {
			//验证结果:数据填充
			model.addAttribute("errorInfo", "用户名或者密码不正确");
			//页面的跳转到 登录界面
			return "/sysadmin/login/login";
		}
		//3/ 把数据放入到session对象中
		System.out.println("************"+user);
		session.setAttribute("sessionUser", user);
		
		return "redirect:/home.action";
	}*/
}
