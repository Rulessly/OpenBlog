package com.blog.mapper;

import com.blog.domain.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author xyxiaobiao
* @description 针对表【sg_article(文章表)】的数据库操作Mapper
* @createDate 2022-11-22 15:51:16
* @Entity com.blog.domain.Article
*/

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

}




