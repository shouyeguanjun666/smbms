package cn.kgc.tangcco.service.role;

import cn.kgc.tangcco.dao.role.RoleMapper;
import cn.kgc.tangcco.pojo.Role;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Override
    public List<Role> findRoleList() {


        return roleMapper.getRoleList();
    }




}
