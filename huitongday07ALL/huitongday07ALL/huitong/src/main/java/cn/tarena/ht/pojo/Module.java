package cn.tarena.ht.pojo;

public class Module extends BaseEntity{
	
	private String moduleId;
	private String name;
	private Integer ctype;
	private Integer state;
	private Integer orderNo;
	private String remark;
	//描述自关联关系
	private Module parentModule;

	/*
	 * zTreeJson对象：id/pId/checked
	 */
	private String id;//moduleId
	private String pId;//上级模块的moduleId
	private boolean checked;//默认false
	
	
	
	public String getId() {
		return moduleId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		//先判断上级部门是否为null
		return parentModule == null? "0" : parentModule.moduleId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCtype() {
		return ctype;
	}

	public void setCtype(Integer ctype) {
		this.ctype = ctype;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public Module getParentModule() {
		return parentModule;
	}

	public void setParentModule(Module parentModule) {
		this.parentModule = parentModule;
	}

	@Override
	public String toString() {
		return "Module [moduleId=" + moduleId + ", name=" + name + ", ctype=" + ctype + ", state=" + state
				+ ", orderNo=" + orderNo + ", parentModule=" + parentModule + "]";
	}
	
	
}
