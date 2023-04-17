package com.blog.service;

import com.blog.domain.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.domain.ResponseResult;

/**
* @author xyxiaobiao
* @description 针对表【sg_category(分类表)】的数据库操作Service
* @createDate 2022-11-23 15:29:42
*/
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

    ResponseResult listAllCategory();

    ResponseResult getCategorylist(Integer pageNum, Integer pageSize, Category category);

    ResponseResult AddCategory(Category category);

    ResponseResult getCategoryInfoById(Long id);

    ResponseResult changeStatus(Category category);
}
