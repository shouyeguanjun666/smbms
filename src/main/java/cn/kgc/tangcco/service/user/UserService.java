package cn.kgc.tangcco.service.user;

import cn.kgc.tangcco.pojo.User;


import java.util.List;


public interface UserService {
	/**
	 * 增加用户信息
	 *
	 * @param user
	 * @return
	 */
	public boolean add(User user);

	/**
	 * 用户登录
	 *
	 * @param userCode
	 * @param userPassword
	 * @return
	 */
	public User login(String userCode, String userPassword);

	/**
	 * 根据条件查询用户列表
	 *
	 * @param queryUserName
	 * @param queryUserRole
	 * @return
	 */
	public List<User> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int pageSize);

	/**
	 * 根据条件查询用户表记录数
	 *
	 * @param queryUserName
	 * @param queryUserRole
	 * @return
	 */
	public int getUserCount(String queryUserName, int queryUserRole);

	//根据id查询用户
	User findUserById(Integer id)throws Exception;


	//根据id删除用户
	boolean remoUser(Integer id)throws Exception;


	//修改用户
	boolean updateUser(User user);

	//修改密码
	boolean updatePwd(User user);


}