package cn.tarena.ht.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tarena.ht.mapper.UserMapper;
import cn.tarena.ht.pojo.User;
import cn.tarena.ht.pojo.UserInfo;
import cn.tarena.ht.tool.Md5Password;
import cn.tarena.ht.tool.PageBean;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public List<User> findAll() {
		return userMapper.findAll();
	}

	@Override
	public int getUserTotalCount() {
		return userMapper.getUserTotalCount();
	}

	//返回值为：pageBean的对象
	//不只是 List<User>  还有 分页的相关数据
	@Override
	public PageBean<User> findUserByPages(int currentPage, int pageSize) {
		PageBean<User> pageBean = new PageBean<User>();
		//封装当前的页数
		pageBean.setCurrPage(currentPage);
		//封装每一页的数量
		pageBean.setPageSize(pageSize);
		//封装用户的总数据量
		int totalCount = userMapper.getUserTotalCount();
		pageBean.setTotalCount(totalCount);
		//封装所有的页数
		int totalPage = (int) Math.ceil(totalCount/pageSize)+1;
		pageBean.setTotalPage(totalPage);
		//封装每一页显示的数据
		//比如 ： 从第3也开始 ，每一页10条 ，那么 开始条数为21
		int startRow = currentPage == 1? 0 : pageSize*(currentPage-1) ;
		List<User> userList = userMapper.findUserByPages(startRow, pageSize);
		pageBean.setPageList(userList);
		return pageBean;
	}

	@Override
	public List<User> findManagerUser() {
		return userMapper.findManagerUser();
	}
	
	public void saveUser(User user) {
		//自动的生成主键
		String userId = UUID.randomUUID().toString();
		//取出用户的密码：明文
		String pass = user.getPassword();
		//加密
		String password = Md5Password.getMd5HashPassword(pass, user.getUsername());
		//保存 到 user_p表中
		user.setUserId(userId);
		user.setPassword(password);
		
		userMapper.saveUser(user);
		//保存 到 user_info_p表中
		UserInfo userInfo = user.getUserInfo();
		//把自动生成的id放入userinfo中
		userInfo.setUserInfoId(userId);
		userMapper.saveUserInfo(userInfo);
	}

	@Override
	public void saveUserRole(String userId, String roleIds) {
		//防止数据的重复插入
		//在每一插入数据之前，删除userid对应的中间表的所有信息
		userMapper.deleteUserRole(userId);
		// roleIds 为 多个 roleId的拼接字符串
		// 数组中存取了 每一个 roleId的信息
		String[] roles = roleIds.split(",");
		//遍历数组
		for (String roleId : roles) {
			userMapper.saveUserRole(userId, roleId); 
		}
		
	}

	@Override
	public List<String> findUserRoleByUserId(String userId) {
		return userMapper.findUserRoleByUserId(userId);
	}

	@Override
	public User checkLoginByUser(String userName,String password) {
		return userMapper.checkLoginByUser(userName, password);
	}

	@Override
	public User findUserByUsername(String username) {
		// TODO Auto-generated method stub
		return userMapper.findUserByUsername(username);
	}

	@Override
	public List<String> findModuleNameByUserId(String userId) {
		return userMapper.findModuleNameByUserId(userId);
	}
	
	
}
