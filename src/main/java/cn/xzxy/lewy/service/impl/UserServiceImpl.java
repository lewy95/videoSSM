package cn.xzxy.lewy.service.impl;

import cn.xzxy.lewy.mapper.UserMapper;
import cn.xzxy.lewy.pojo.User;
import cn.xzxy.lewy.pojo.User_Role;
import cn.xzxy.lewy.service.UserService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService{

    @Autowired
    UserMapper userMapper;

    @Override
    public User findUserByEmail(String email) {

        return userMapper.selectUserByEmail(email);
    }

    @Override
    public User_Role findUserRoleByEmail(String email) {
        return userMapper.selectUserRoleByEmail(email);
    }

    @Override
    public void addUser(User user) {
        userMapper.insertUser(user);
    }

    @Override
    public void addUserRole(User_Role user_role) {
        userMapper.insertUserRole(user_role);
    }

    @Override
    @RequiresRoles(value = {"admin"})
    public void testIsAuthorizedMethod() {
        System.out.println("您拥有该权限，访问成功");
    }

}
