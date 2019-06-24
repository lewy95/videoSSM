package cn.xzxy.lewy.util;

import java.util.LinkedHashMap;

public class FilterChainDefinitionMapBuilder {

    public LinkedHashMap<String, String> buildFilterChainDefinitionMap(){
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

        //依旧满足url优先匹配原则，所以需要注意顺序 LinkedHashMap可以保持顺序
        map.put("/css/**","anon");
        map.put("/fonts/**","anon");
        map.put("/images/**","anon");
        map.put("/js/**","anon");
        map.put("/","anon");//访问欢迎页
//        map.put("/index.jsp","anon");//访问欢迎页,可以不写
        map.put("/forehome","anon");//无登陆时访问首页
        map.put("/toLoginPage","anon");//跳转至登录界面
        map.put("/toRegisterPage","anon");//跳转至注册界面
        map.put("/foreRegister","anon");//用户注册
        map.put("/foreLogin","anon");//用户登录

        map.put("/logout","logout");//用户登出
        //认证之后同时还有user的权限才可以访问，关闭浏览器后用记住我登陆时不行的
//        map.put("/user.jsp", "authc,roles[user]");
//        map.put("/admin.jsp", "authc,roles[admin]");
        //关闭浏览器后用记住我登陆时可以
        //map.put("/list.jsp", "user");

        map.put("/**", "authc");

        return map;
    }

}
