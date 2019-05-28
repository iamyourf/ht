package cn.tarena.ht.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

import cn.tarena.ht.tool.Md5Password;

//shiro 安全中心 根据用户的密码 ， 进行内部的密码规则的校验
public class AuthCredential extends SimpleCredentialsMatcher{
	
	//验证加密
	//程序员进行方法的重写：内部的密码规则
	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		//通过令牌获取登录的对象
		UsernamePasswordToken userToken = (UsernamePasswordToken) token;
		//获取用户名和密码
		String username = userToken.getUsername();
		String password = String.valueOf(userToken.getPassword());
		System.out.println("令牌的明文密码："+password);
		// 使用md5算法进行加密
		// 使用了加密的工具类：
		String md5password = Md5Password.getMd5HashPassword(password, username);
		userToken.setPassword(md5password.toCharArray());
		//调用父类的内部校验规则：令牌和登录认证的原材料info信息
		return super.doCredentialsMatch(token, info);
	}
	
}
