package cn.xzxy.lewy.mapper;

import cn.xzxy.lewy.pojo.Video;

import java.util.List;

public interface VideoMapper {

    //查出所有视频
    List<Video> selectAllVideo();

    //根据id查出视频
    Video selectVideoById(Integer id);

    //根据id更新评论数目
    void updateComNum(Video video);

    //新增视频
    int insert(Video record);

    Video selectByPrimaryKey(Integer id);

}
