package cn.xzxy.lewy.service;

import cn.xzxy.lewy.pojo.Video;

import java.util.List;

public interface VideoService {

    List<Video> listVideo();

    Video findVideo(Integer id);

    void updateVCom(Video video);

    /**
     * 视频转码
     * @param ffmpegPath    转码工具的存放路径
     * @param upFilePath    用于指定要转换格式的文件,要截图的视频源文件
     * @param codcFilePath    格式转换后的的文件保存路径
     * @param mediaPicPath    截图保存路径
     * @return
     * @throws Exception
     */
    public boolean executeCodecs(String ffmpegPath, String upFilePath, String codcFilePath, String mediaPicPath)throws Exception;

    /**
     * 保存文件
     * @param video
     * @return
     * @throws Exception
     */
    public boolean saveVideo(Video video)throws Exception;


    /**
     * 根据Id查询视频
     * @param id
     * @return
     * @throws Exception
     */
    public Video queryVideoById(int id)throws Exception;
}
