package com.fuzijian.community.dao;

import com.fuzijian.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.ListIterator;

@Mapper
public interface DiscussPostMapper {

    // 查询的功能
    // 考虑查询到个人的所有帖子，以及分页的需求
    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);

    // 查询出表中一共有多少条数据
    // Param注解用于给参数取别名，如果方法只有一个参数，并且在<if>标签中使用，就必须加别名注释
    int selectDiscussPostRows(@Param("userId") int userId);

}
