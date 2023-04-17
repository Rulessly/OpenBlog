package com.blog.controller;


import com.blog.Vo.AddArticleDto;
import com.blog.Vo.AdminArticleVo;
import com.blog.domain.Article;
import com.blog.domain.ResponseResult;
import com.blog.domain.SysMenu;
import com.blog.domain.dto.AdminArticleDto;
import com.blog.domain.dto.ArticleDto;
import com.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    @PostMapping
    public ResponseResult addArticle(@RequestBody Article article){
        return articleService.addArticle(article);
    }

    @GetMapping("/list")
    public ResponseResult getArticleList(Integer pageNum, Integer pageSize,ArticleDto articleDto){
        return articleService.getArticleList(pageNum,pageSize,articleDto);
    }


    @GetMapping("{id}")
    public ResponseResult selectUpdateArticle(@PathVariable("id") Long id){
        return articleService.selectUpdateArticle(id);
    }


    @PutMapping
    public ResponseResult updateArticle(@RequestBody AdminArticleDto articleDto){
        return articleService.updateArticle(articleDto);
    }

    @DeleteMapping("{id}")
    public ResponseResult deleteArticle(@PathVariable("id") Long id){
        return articleService.deleteArticle(id);
    }

}
