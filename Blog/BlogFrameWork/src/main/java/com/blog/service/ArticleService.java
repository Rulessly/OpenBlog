package com.blog.service;

import com.blog.Vo.AddArticleDto;
import com.blog.domain.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.domain.ResponseResult;
import com.blog.domain.dto.AdminArticleDto;
import com.blog.domain.dto.ArticleDto;

/**
* @author xyxiaobiao
* @description 针对表【sg_article(文章表)】的数据库操作Service
* @createDate 2022-11-22 15:51:16
*/
public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();
    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult addArticle(Article article);

    ResponseResult getArticleList(Integer pageNum, Integer pageSize, ArticleDto articleDto);

    ResponseResult selectUpdateArticle(Long id);

    ResponseResult updateArticle(AdminArticleDto articleDto);

    ResponseResult deleteArticle(Long id);

}
