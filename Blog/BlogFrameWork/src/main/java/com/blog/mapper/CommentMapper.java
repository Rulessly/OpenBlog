package com.blog.mapper;

import com.blog.domain.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author xyxiaobiao
* @description 针对表【sg_comment(评论表)】的数据库操作Mapper
* @createDate 2022-11-29 16:40:33
* @Entity com.blog.domain.Comment
*/
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}




