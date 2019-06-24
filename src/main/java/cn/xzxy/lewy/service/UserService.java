package cn.xzxy.lewy.service;

import cn.xzxy.lewy.pojo.User;
import cn.xzxy.lewy.pojo.User_Role;

public interface UserService {

    User findUserByEmail(String email);

    User_Role findUserRoleByEmail(String email);

    void addUser(User user);

    void addUserRole(User_Role user_role);

    void testIsAuthorizedMethod();

}
