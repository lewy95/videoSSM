package cn.xzxy.lewy.mapper;

import cn.xzxy.lewy.pojo.User;
import cn.xzxy.lewy.pojo.User_Role;

import java.util.List;

public interface UserMapper {

    //根据email查找用户信息
    User selectUserByEmail(String email);

    //根据email查找用户角色信息
    User_Role selectUserRoleByEmail(String email);

    //添加新用户，即注册
    void insertUser(User user);

    //添加新用户角色信息，随注册时一起添加
    void insertUserRole(User_Role user_role);

}
