package cn.xzxy.lewy.service.impl;

import cn.xzxy.lewy.mapper.VideoCommentMapper;
import cn.xzxy.lewy.pojo.VideoComment;
import cn.xzxy.lewy.service.VideoCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("videoCommentService")
public class VideoCommentServiceImpl implements VideoCommentService{

    @Autowired
    VideoCommentMapper videoCommentMapper;

    @Override
    public List<VideoComment> listALLVideoComment(Integer id) {

        return videoCommentMapper.selectALLVideoCommentByVid(id);
    }

    @Override
    public void addVComment(VideoComment videoComment) {

        videoCommentMapper.insertVComment(videoComment);
    }


}
