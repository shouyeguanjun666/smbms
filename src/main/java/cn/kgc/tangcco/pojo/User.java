package cn.kgc.tangcco.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


public class User {
	private Integer id;//主键ID
	private String userCode;//用户编码
	private String userName;//用户名称
	private String userPassword;//用户密码
	private Integer gender;//性别1.女2.男
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	private Date birthday;//出生日期
	private String phone;//手机
	private String address;//地址
	private Integer userRole;//用户角色（取自角色表-角色id）
	private Integer createdBy;//创建者(userId)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date creationDate;//创建时间
	private String modifyBy;//更新者(userId)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date modifyDate;//更新时间
	private String idPicPath;//身份证件路径
	private String workPicPath;//工作照路径
	//为了显示角色名称添加的属性
	private String userRoleName;
	//获得年龄
	public Integer getAge(){
		Date date=new Date();
		Integer age=date.getYear()-birthday.getYear();
		return age;

	}

	public String getUserRoleName() {
		return userRoleName;
	}
	public void setUserRoleName(String userRoleName) {
		this.userRoleName = userRoleName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getUserRole() {
		return userRole;
	}
	public void setUserRole(Integer userRole) {
		this.userRole = userRole;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getIdPicPath() {
		return idPicPath;
	}
	public void setIdPicPath(String idPicPath) {
		this.idPicPath = idPicPath;
	}
	public String getWorkPicPath() {
		return workPicPath;
	}
	public void setWorkPicPath(String workPicPath) {
		this.workPicPath = workPicPath;
	}
	

}
