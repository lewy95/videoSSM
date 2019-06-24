package cn.xzxy.lewy.controller;

import cn.xzxy.lewy.pojo.User;
import cn.xzxy.lewy.pojo.Video;
import cn.xzxy.lewy.pojo.VideoComment;
import cn.xzxy.lewy.service.UserService;
import cn.xzxy.lewy.service.VideoCommentService;
import cn.xzxy.lewy.service.VideoService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("")
public class VideoCommentController {

    @Autowired
    VideoCommentService videoCommentService;

    @Autowired
    UserService userService;

    @Autowired
    VideoService videoService;

    @RequestMapping("addVideoComment")
    public String addComment(@RequestParam("content")String content,
                             VideoComment videoComment, Integer id, Model model){
        //1.通过Subject获取到用户名
        String email = (String) SecurityUtils.getSubject().getPrincipal();
        //System.out.println(email);

        //2.根据email查询用户信息
        User user = userService.findUserByEmail(email);

        //System.out.println("success to find " + user.getId());
        //System.out.println("jsp传来的值是"+id);

        //3.添加评论
        videoComment.setUid(user.getId());
        videoComment.setVid(id);
        videoComment.setContent(content);
        videoComment.setCreateDate(new Date());
        videoCommentService.addVComment(videoComment);

        //4.修改评论数目
        Video video = videoService.findVideo(id);
        int oldCommentsNum = video.getCommentsNum();
        video.setCommentsNum(oldCommentsNum + 1);
        videoService.updateVCom(video);

        return "redirect:video_detail?id="+id;
    }
}
