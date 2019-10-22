package cn.kgc.tangcco.service.user;

import cn.kgc.tangcco.dao.user.UserMapper;
import cn.kgc.tangcco.pojo.User;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * service层捕获异常，进行事务处理
 * 事务处理：调用不同dao的多个方法，必须使用同一个connection（connection作为参数传递）
 * 事务完成之后，需要在service层进行connection的关闭，在dao层关闭（PreparedStatement和ResultSet对象）
 * @author Administrator
 *
 */
@Service
public class UserServiceImpl implements UserService{
	@Resource
	private UserMapper userMapper;


	@Override
	public boolean add(User user) {
		boolean flag=false;
		int result=userMapper.add(user);
		if(result>0){
			flag=true;
		}

		return flag;
	}

	@Override
	public User login(String userCode, String userPassword) {

		User user=userMapper.getLoginUser(userCode);
		if(!user.getUserPassword().equals(userPassword)){
			user=null;
		}

		return user;



	}

	//需要动态sql
	@Override
	public List<User> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int pageSize) {
		return userMapper.getUserList(queryUserName,queryUserRole,currentPageNo,pageSize);
	}

	@Override
	public int getUserCount(String queryUserName, int queryUserRole) {
		return userMapper.getUserCount(queryUserName,queryUserRole);
	}

    @Override
    public User findUserById(Integer id) throws Exception {
        return userMapper.getUserById(id);
    }

    @Override
    public boolean remoUser(Integer id) throws Exception {

        System.out.println("ssssssssssssss");
	    int result=userMapper.delUser(id);
	    if(result>0){
	        return true;
        }
        return false;
    }

	@Override
	public boolean updateUser(User user) {

		int result=userMapper.updateUser(user);
		if(result>0){
			return true;
		}

		return false;
	}

	@Override
	public boolean updatePwd(User user) {
		int result=userMapper.updatePwd(user);
		if(result>0){
			return true;
		}

		return false;
	}
}
