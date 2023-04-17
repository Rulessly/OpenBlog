package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.Vo.*;
import com.blog.contants.SystemConstants;
import com.blog.domain.*;
import com.blog.domain.dto.AdminArticleDto;
import com.blog.domain.dto.ArticleDto;
import com.blog.enums.AppHttpCodeEnum;
import com.blog.service.ArticleService;
import com.blog.mapper.ArticleMapper;
import com.blog.service.ArticleTagService;
import com.blog.service.CategoryService;
import com.blog.utils.BeanCopyUtils;
import com.blog.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* @author xyxiaobiao
* @description 针对表【sg_article(文章表)】的数据库操作Service实现
* @createDate 2022-11-22 15:51:16
*/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
    implements ArticleService{


    @Autowired
    private ArticleTagService articleTagService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult hotArticleList() {
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        queryWrapper.orderByDesc(Article::getViewCount);
        Page<Article> page=new Page<>(1,10);
        page(page,queryWrapper);
        List<Article> articles = page.getRecords();
//        List<HotArticleDto> hotArticleDtos=new ArrayList<>();
//        for (Article article : articles) {
//            HotArticleDto hotArticleDto=new HotArticleDto();
//            BeanUtils.copyProperties(article,hotArticleDto);
//            hotArticleDtos.add(hotArticleDto);
//        }
        List<HotArticleVo> articleDtos = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);

        return ResponseResult.okResult(articleDtos);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 如果 有categoryId 就要 查询时要和传入的相同
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0 ,Article::getCategoryId,categoryId);
        // 状态是正式发布的
        lambdaQueryWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        // 对isTop进行降序
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);

        //分页查询
        Page<Article> page = new Page<>(pageNum,pageSize);
        page(page,lambdaQueryWrapper);

        List<Article> articles = page.getRecords();
        articles.stream().map(article ->{
            Long id = article.getCategoryId();
            String name = categoryService.getById(id).getName();
            article.setCategoryName(name);
            return article;
        }).collect(Collectors.toList());

//        //封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);

        PageVo pageVo = new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据id查询文章
        Article article = getById(id);
        //从redis中获取viewcount
        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", id.toString());
        article.setViewCount(viewCount.longValue());

        //转换成Vo
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);

        //根据分类id查询分类名
        Long categoryId = articleDetailVo.getCategoryId();
        Category byId = categoryService.getById(categoryId);
        if(byId!=null){
            articleDetailVo.setCategoryName(byId.getName());
        }
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        //更新redis对应的id浏览量
        redisCache.incrementCacheMapValue("article:viewCount",id.toString(),1);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult addArticle(Article article) {
//        //添加博客
//        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        save(article);

        List<ArticleTag> articleTags = article.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());

        //添加博客和标签的关联
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getArticleList(Integer pageNum, Integer pageSize, ArticleDto articleDto) {
//        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
//        //根据文章标题和摘要进行查询(或者模糊查询)
//        queryWrapper.like(StringUtils.hasText(articleDto.getTitle()),Article::getTitle,articleDto.getTitle());
//        queryWrapper.like(StringUtils.hasText(articleDto.getSummary()),Article::getSummary,articleDto.getSummary());
//        //查询状态为0的文章（未被逻辑删除的文章）
//        queryWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
//        Page<Article> page=new Page<>(pageNum,pageSize);
//        page(page,queryWrapper);
//
//        //将Article对象转换为ArticleDetailsVo对象
//        List<Article> records = page.getRecords();
//        List<ArticleDetailVo> articleDetailVos = BeanCopyUtils.copyBeanList(records, ArticleDetailVo.class);
//        PageVo pageVo=new PageVo(articleDetailVos,page.getTotal());
//        return ResponseResult.okResult(pageVo);
        //        1.根据文章标题(模糊查询)和摘要进行查询
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(articleDto.getTitle()), Article::getTitle, articleDto.getTitle());
        queryWrapper.like(StringUtils.hasText(articleDto.getSummary()), Article::getSummary, articleDto.getSummary());
//        2.规定文章是未发布状态不能显示
        queryWrapper.eq(Article::getStatus,"0");
//        3.分页查询
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);

//        3.将当前页中的Article对象转换为ArticleDetailsVo对象
        List<Article> articles = page.getRecords();
        List<ArticleDetailVo> articleDetailsVos = BeanCopyUtils.copyBeanList(articles, ArticleDetailVo.class);
//        4.将LinkVo对象转换为LinkAdminVo对象
        AdminArticleVo adminArticleVo = new AdminArticleVo(articleDetailsVos, page.getTotal());
        return ResponseResult.okResult(adminArticleVo);
    }

    @Override
    public ResponseResult selectUpdateArticle(Long id) {
        //获取文章的id
        Article article = getById(id);

        //将article转换成UpdateArticleVo
        UpdateArticleVo updateArticleVo = BeanCopyUtils.copyBean(article, UpdateArticleVo.class);

        //根据文章id获取文章标签
        List<Long> tagList = articleTagService.getTagList(id);
        updateArticleVo.setTags(tagList);
        return ResponseResult.okResult(updateArticleVo);
    }

    @Override
    public ResponseResult updateArticle(AdminArticleDto articleDto) {
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        //        2.将博客的标签信息存入标签表
//          2.1根据当前博客id获取到已有的标签列表
        List<Long> tagList = articleTagService.getTagList(article.getId());
//          2.2得到修改过后的标签列表
        List<Long> tags = article.getTags();//因原实体类Article中没有tags，所有在实体类中手动添加
//          2.3遍历修改过后的标签列表，判断当前博客是否已经有此标签，没有则一条数据添加到sg_article_tag表中
        for (Long tag:tags){
            if (!tagList.contains(tag)){
                articleTagService.save(new ArticleTag(article.getId(), tag));
            }
        }
        updateById(article);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteArticle(Long id) {
        boolean b = removeById(id);
        if(b==false){
            return ResponseResult.errorResult(AppHttpCodeEnum.ARTICLE_DELETE_ERROR);
        }
        return ResponseResult.okResult();
    }



}





