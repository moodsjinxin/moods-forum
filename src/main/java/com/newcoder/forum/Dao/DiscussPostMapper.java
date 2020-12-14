package com.newcoder.forum.Dao;

import com.newcoder.forum.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface DiscussPostMapper {

    List<DiscussPost> selectDicussPosts(int userId, int offset, int limit, int orderMode);

    // Param 注解用于给参数取名
    // 如果只有一个参数,必须在《if》里使用,则必须加别名
    int selectDiscussPostRows(@Param("userId") int userId);

    int insertDiscussPost(DiscussPost discussPost);

    DiscussPost selectDisscussById(int id);
    int updateCommentCount(int id, int commentCount);

    int updateType(int id, int type);

    int updateStatus(int id, int status);

    int updateScore(int id, double score);
}
