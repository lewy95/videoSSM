package cn.xzxy.lewy.controller;

import cn.xzxy.lewy.pojo.User;
import cn.xzxy.lewy.pojo.Video;
import cn.xzxy.lewy.service.UserService;
import cn.xzxy.lewy.service.VideoService;
import cn.xzxy.lewy.util.MyWebPrinter;
import org.apache.commons.fileupload.util.Streams;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

@Controller
@RequestMapping("")
public class UploadController {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(UploadController.class);

    @Autowired
    private VideoService videoService;

    @Autowired
    private UserService userService;

    /**
     * 视频上传,解码并保存
     */
    @RequestMapping("dofunction")
    public void handler(HttpServletRequest request, HttpServletResponse response,
                        @RequestParam("myvideo") MultipartFile file) throws IOException {
        String message = "";
        try {
            Video video = new Video();
            // 解析数据
            video.setName(request.getParameter("name"));
            video.setDescription(request.getParameter("description"));
            boolean flag = false; // 转码成功与否的标记
            // 上传文件
            ServletContext sctx = request.getServletContext();
            // 获得保存文件的路径
            String basePath = sctx.getRealPath("videos");
            //System.out.println(basePath);//D:\mavenWorkSet\videoTest\target\videoTest\videos
            // 获得文件名
            String fileUrl = file.getOriginalFilename();
            //System.out.println(fileUrl);
            // 在某些操作系统上,item.getName()方法会返回文件的完整名称,即包括路径
            String fileType = fileUrl.substring(fileUrl.lastIndexOf(".")); // 截取文件格式
            //System.out.println(fileType);
            // 自定义方式产生文件名
            String serialName = String.valueOf(System.currentTimeMillis());
            //System.out.println(serialName);
            // 待转码的文件
            File uploadFile = new File(basePath + "/temp/" + serialName + fileType);
            System.out.println(uploadFile.getAbsolutePath());

            // 保存文件
            Streams.copy(file.getInputStream(),new FileOutputStream(uploadFile.getAbsolutePath()),true);
            // 判断文件的大小
            if (file.getSize() > 500 * 1024 * 1024) {
                message = "上传失败！您上传的文件太大,系统允许最大文件500M";
            }
            String codeFilePath = basePath + "/" + serialName + ".flv"; // 设置转换为flv格式后文件的保存路径
            String mediaPicPath = basePath + "/images" + File.separator + serialName + ".jpg"; // 设置上传视频截图的保存路径

            // 获取配置的转换工具（ffmpeg.exe）的存放路径
            String ffmpegPath = request.getServletContext().getRealPath("/tools/ffmpeg.exe");

            video.setAddress("videos/" + serialName + ".flv");
            //视频截图是假的，时间有限以后再做，目前的截图是页面写死的
            video.setPicture("videos/images/" + serialName + ".jpg");
            video.setUptime(new Date());
            video.setCommentsNum(0);//当初评论为0

            //创建人即当前登录者，获取他的名字，并设置给video的creator属性
            String email = (String) SecurityUtils.getSubject().getPrincipal();
            User user = userService.findUserByEmail(email);
            video.setCreator(user.getUsername());

            // 转码
            flag = videoService.executeCodecs(ffmpegPath, uploadFile.getAbsolutePath(),
                    codeFilePath, mediaPicPath);

            if (flag) {
                // 转码成功,向数据表中添加该视频信息
                videoService.saveVideo(video);
                message="上传成功";
            }
            request.setAttribute("message", message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        MyWebPrinter.print(response,"<script>alert('"+message+"');window.location.href='forehome';</script>");
    }

}


