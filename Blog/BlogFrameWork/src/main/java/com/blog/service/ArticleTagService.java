package com.blog.service;

import com.blog.domain.ArticleTag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 22410
* @description 针对表【sg_article_tag(文章标签关联表)】的数据库操作Service
* @createDate 2023-02-10 18:43:41
*/

public interface ArticleTagService extends IService<ArticleTag> {

    List<Long> getTagList(Long id);
}
