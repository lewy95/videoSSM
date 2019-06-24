package cn.xzxy.lewy.service;

import cn.xzxy.lewy.pojo.VideoComment;

import java.util.List;

public interface VideoCommentService {

    List<VideoComment> listALLVideoComment(Integer id);

    void addVComment(VideoComment videoComment);
}
