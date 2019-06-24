package cn.xzxy.lewy.realm;

import cn.xzxy.lewy.pojo.User;
import cn.xzxy.lewy.pojo.User_Role;
import cn.xzxy.lewy.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component("shiroRealm")
public class ShiroRealm extends AuthorizingRealm{

    @Autowired
    UserService userService;

    protected AuthenticationInfo doGetAuthenticationInfo
            (AuthenticationToken authenticationToken) throws AuthenticationException {
        //此处的authenticationToken和controller中的UsernamePasswordToken是同一个，是controller中传过来的
        //System.out.println("doGetAuthenticationInfo " + authenticationToken.hashCode());

        //1. 把 AuthenticationToken 转换为 UsernamePasswordToken
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;

        //2. 从 UsernamePasswordToken 中来获取 username
        String username = upToken.getUsername();

        //3. 调用数据库的方法, 从数据库中查询 username 对应的用户记录（登录名和密码）
        //System.out.println("从数据库中获取 username: " + username + " 所对应的用户信息.");
        User user = userService.findUserByEmail(username);
        System.out.println(user.getEmail() + ", " + user.getPassword());

        //4. 若用户不存在, 则可以抛出 UnknownAccountException 异常
//        if("unknown".equals(username)){
//            throw new UnknownAccountException("用户不存在!");
//        }

        //5. 根据用户信息的情况, 决定是否需要抛出其他的 AuthenticationException 异常
//        if("monster".equals(username)){
//            throw new LockedAccountException("用户被锁定");
//        }

        //6. 根据用户的情况来构建 AuthenticationInfo对象并返回 通常用的实现类为: SimpleAuthenticationInfo
        //以下信息是从数据库中获取的.
        //(1). principal : 认证的实体信息 可以是 username 也可以是数据表对应的用户的实体类对象
        Object principal = username;
        //(2). credentials : 密码.
        Object credentials = null;
        if(user.getEmail().equals(username)){
            credentials = user.getPassword();
        }
        //(3). realmName : 当前realm对象的name 调用父类的getName()方法即可
        String realmName = getName();
        //(4). salt : 盐值 这里用username作为盐值 因为用户名是唯一的
        ByteSource salt = ByteSource.Util.bytes(username);

        SimpleAuthenticationInfo info = null;
        info = new SimpleAuthenticationInfo(principal,credentials,salt,realmName);

        return info;
    }

    //测试获取加密后的密码 本例原密码123456，加密2次
    public static void main(String[] args) {
        String hashAlgorithmName = "MD5";
        Object credentials = "123456";
        //Object salt = ByteSource.Util.bytes("lewy@9.com");//9be0a8423bbe47b9ab62b964d0e5b434
        Object salt = ByteSource.Util.bytes("muller@25.com");//9c377556e3611b4e4fe3d844f1a7135a
        int hashIterations = 2;

        //将一个字符串进行MD5加密
        Object result = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
        System.out.println(result);
    }

    //授权会被shiro回调的方法
    protected AuthorizationInfo doGetAuthorizationInfo
               (PrincipalCollection principalCollection) {
        //1. 从 PrincipalCollection 中来获取登录用户的信息
        //   注意如果是多realm，获取的principal也是有顺序的
        Object principal = principalCollection.getPrimaryPrincipal();

        //2. 利用登录的用户的信息来查用户当前用户的角色或权限(可能需要查询数据库)
        User_Role user_role = userService.findUserRoleByEmail((String) principal);
        System.out.println("角色为：" + user_role.getRole_name());

        Set<String> roles = new HashSet<String>();
        roles.add("user");//给所有用户添加user权限
        if(user_role.getRole_name().equals("admin")){
            roles.add(user_role.getRole_name());//如果用户的角色是admin，再添加一个admin权限
        }

        //3. 创建 SimpleAuthorizationInfo, 并设置其 roles 属性.
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);

        //4. 返回 SimpleAuthorizationInfo 对象.
        return info;
    }
}
