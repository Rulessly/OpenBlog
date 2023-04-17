package com.blog.runner;

import com.blog.domain.Article;
import com.blog.mapper.ArticleMapper;
import com.blog.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ViewCountRunner implements CommandLineRunner {


    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisCache redisCache;
    @Override
    public void run(String... args) throws Exception {
        //查询博客信息id，viewcount
        List<Article> articles = articleMapper.selectList(null);
        Map<String, Integer> viewcountmap = articles.stream().collect(Collectors.toMap(article1 -> article1.getId().toString(), article -> {
            return article.getViewCount().intValue();
        }));


        //存储到redis
        redisCache.setCacheMap("article:viewCount",viewcountmap);
    }


}
