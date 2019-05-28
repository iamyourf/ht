package cn.tarena.ht.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import cn.tarena.ht.pojo.Module;

public interface ModuleMapper {
	public List<Module> findAll();
	//准备所有的上级模块的数据
	@Select(value="select * from module_p where ctype<>3 ")
	public List<Module> findParentModule();
	@Insert(value="insert into module_p (module_id,parent_id,name,ctype,state,order_no,remark)"
			+ " values (#{moduleId},#{parentModule.moduleId},#{name},#{ctype},#{state},#{orderNo},#{remark}) ")
	public void saveModule(Module module);
	
	public List<String> findRoleModuleByRoleId(String roleId);
	
}
