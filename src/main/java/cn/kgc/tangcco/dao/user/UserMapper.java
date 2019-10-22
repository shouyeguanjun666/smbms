package cn.kgc.tangcco.dao.user;


import cn.kgc.tangcco.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserMapper {
    /**
     * 增加用户信息
     */
    public int add(User user);


    /**
     * 通过userCode获取Uses
     */
    public User getLoginUser(String userCode);



    /**
     * 通过条件查询-userList
     */
    public List<User> getUserList(@Param("userName") String userName,@Param("userRole") int userRole, @Param("currentPageNo")int currentPageNo, @Param("pageSize")int pageSize);


    /**
     * 通过条件查询-用户表记录数
     */
    public int getUserCount( @Param("userName")String userName, @Param("userRole")int userRole);

    //根据id查询用户
    User getUserById(Integer id)throws Exception;


    //根据id删除用户
    int delUser(Integer id);

    //修改用户
    int updateUser(User user);

    //修改密码
    int updatePwd(User user);




}
