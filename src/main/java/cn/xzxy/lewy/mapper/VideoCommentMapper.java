package cn.xzxy.lewy.mapper;

import cn.xzxy.lewy.pojo.VideoComment;

import java.util.List;

public interface VideoCommentMapper {

    //查找评论所有评论
    List<VideoComment> selectALLVideoCommentByVid(Integer id);

    //增加评论
    void insertVComment(VideoComment videoComment);

}
