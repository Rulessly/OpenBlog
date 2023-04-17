package com.blog.mapper;

import com.blog.domain.ArticleTag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 22410
* @description 针对表【sg_article_tag(文章标签关联表)】的数据库操作Mapper
* @createDate 2023-02-10 18:43:41
* @Entity com.blog.domain.ArticleTag
*/

@Mapper
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {

}




