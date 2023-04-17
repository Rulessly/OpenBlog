package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.Vo.CategoryVo;
import com.blog.Vo.PageVo;
import com.blog.contants.SystemConstants;
import com.blog.domain.Article;
import com.blog.domain.Category;
import com.blog.domain.ResponseResult;
import com.blog.domain.User;
import com.blog.service.ArticleService;
import com.blog.service.CategoryService;
import com.blog.mapper.CategoryMapper;
import com.blog.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.blog.contants.SystemConstants.STATUS_NORMAL;
import static com.blog.enums.AppHttpCodeEnum.CATEGORY_EXIST;


/**
* @author xyxiaobiao
* @description 针对表【sg_category(分类表)】的数据库操作Service实现
* @createDate 2022-11-23 15:29:42
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public ResponseResult getCategoryList() {
        //查询文章表，查询正常发布的文章
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(queryWrapper);
        //获取文章分类id，并且去重
        Set<Long> categoryid = articleList.stream().map(article -> article.getCategoryId()).collect(Collectors.toSet());

        //查询分类表,并且查询有正常发布的文章状态
        List<Category> categories = listByIds(categoryid);
        categories=categories.stream().filter(category -> STATUS_NORMAL.equals(category.getStatus())).collect(Collectors.toList());

        //封装Dto
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);

        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public ResponseResult listAllCategory() {
        //查询所有分类
        LambdaQueryWrapper<Category> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getStatus,STATUS_NORMAL);
        List<Category> categoryList = categoryMapper.selectList(queryWrapper);
        //将categoryList对象转换为categoryvo封装返回
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categoryList, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public ResponseResult getCategorylist(Integer pageNum, Integer pageSize, Category category) {
        LambdaQueryWrapper<Category> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(category.getStatus()),Category::getStatus,category.getStatus());
        queryWrapper.like(StringUtils.hasText(category.getName()),Category::getName,category.getName());
        Page<Category> page=new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        List<Category> records = page.getRecords();
        List<Category> categories = BeanCopyUtils.copyBeanList(records, Category.class);
        PageVo pageVo=new PageVo(categories,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult AddCategory(Category category) {
        LambdaQueryWrapper<Category> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getName,category.getName());
        Category one = getOne(queryWrapper);
        if(!Objects.isNull(one)){
            return ResponseResult.errorResult(CATEGORY_EXIST);
        }
        save(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getCategoryInfoById(Long id) {
        LambdaQueryWrapper<Category> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getId,id);
        return ResponseResult.okResult(getById(id));
    }

    @Override
    public ResponseResult changeStatus(Category category) {
        LambdaUpdateWrapper<Category> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.eq(Category::getId,category.getId())
                     .set(Category::getStatus,category.getStatus());
        update(null,updateWrapper);
        return ResponseResult.okResult("修改成功");
    }


}




