package cn.xzxy.lewy.controller;

import cn.xzxy.lewy.pojo.User;
import cn.xzxy.lewy.pojo.User_Role;
import cn.xzxy.lewy.pojo.Video;
import cn.xzxy.lewy.pojo.VideoComment;
import cn.xzxy.lewy.service.UserService;
import cn.xzxy.lewy.service.VideoCommentService;
import cn.xzxy.lewy.service.VideoService;
import cn.xzxy.lewy.util.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("")
public class HomeController {

    @Autowired
    VideoService videoService;

    @Autowired
    VideoCommentService videoCommentService;

    @Autowired
    UserService userService;

    @RequestMapping("forehome")
    public String home(Model model, Page page) {
        PageHelper.offsetPage(page.getStart(),page.getCount());//需要写在查询之前
        List<Video> videos = videoService.listVideo();
        int total = (int) new PageInfo<>(videos).getTotal();
        page.setTotal(total);
        model.addAttribute("vs",videos);
        model.addAttribute("page",page);

        return "video";
    }

    /**
     * 视频播放
     */
    @RequestMapping("video_detail")
    public String videoDetail(Integer id, Model model){

        //查找视频的简介
        Video video = videoService.findVideo(id);
        model.addAttribute("v",video);

        //查找该视频的评论
        List<VideoComment> videoComments = videoCommentService.listALLVideoComment(id);
        model.addAttribute("vcs",videoComments);

        return "video-detail";
    }

    //跳转到登陆界面
    @RequestMapping("toLoginPage")
    public String RedirectToLoginPage(){
        return "loginPage";
    }

    //跳转到注册界面
    @RequestMapping("toRegisterPage")
    public String RedirectToRegisterPage(){
        return "registerPage";
    }

    //注册
    @RequestMapping("foreRegister")
    public String register(User user) {
        String name = user.getUsername();
        String password = user.getPassword();
        String email = user.getEmail();

        System.out.println(name+","+password+","+email);

        if(name.equals("") || password.equals("") || email.equals("")) {
            System.out.println("注册失败!");

            return "redirect:toRegisterPage";

        }else{
            //将密码进行MD5盐值加密
            String hashAlgorithmName = "MD5";
            Object credentials = password;
            Object salt = ByteSource.Util.bytes(email);
            int hashIterations = 2;
            Object result = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);

            //设置密码为加密后密码
            user.setPassword(result.toString());
            //添加用户信息
            userService.addUser(user);
            //添加用户角色信息
            User_Role ur = new User_Role();
            ur.setEmail(email);
            ur.setRole_name("user");//注册的用户角色统一为user
            userService.addUserRole(ur);
            System.out.println("注册成功！");

            return "redirect:toLoginPage";

        }
    }

    //登录
    @RequestMapping("foreLogin")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password, HttpSession session) {
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            // 把用户名和密码封装为 UsernamePasswordToken 对象
            UsernamePasswordToken token = new UsernamePasswordToken(email, password);

            //开启记住我功能，实际上把一个cookie写到了客户端，关闭浏览器后可以登陆成功
            token.setRememberMe(true);

            try {
                //System.out.println(token.hashCode());
                // 执行登录.
                currentUser.login(token);
                session.setAttribute("user",currentUser);
                System.out.println("登陆成功！");
            }
            // 所有认证时异常的父类.
            catch (AuthenticationException ae) {
                System.out.println("登录失败: " + ae.getMessage());
                return "redirect:toLoginPage";
            }
        }

        return "redirect:forehome";
    }

    //在用户信息处测试是否有权限
    @RequestMapping("testAuthorized")
    public String testIsAuthorized(){
        userService.testIsAuthorizedMethod();
        return "hasRole";
    }

}
