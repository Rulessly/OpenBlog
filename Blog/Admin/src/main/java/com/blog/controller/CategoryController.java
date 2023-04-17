package com.blog.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.blog.Vo.ExcelCategoryVo;
import com.blog.domain.Category;
import com.blog.domain.ResponseResult;
import com.blog.enums.AppHttpCodeEnum;
import com.blog.service.CategoryService;
import com.blog.utils.BeanCopyUtils;
import com.blog.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/content/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory(){
        return categoryService.listAllCategory();
    }


    @PreAuthorize("@ps.hasPermisson('content:category:export')")
    @GetMapping("/export")
    public void export(HttpServletResponse response){

        try {
            //设置下载文件的请求头
            WebUtils.setDownLoadHeader("分类.xlsx",response);
            //获取需要导出的数据
            List<Category> categories = categoryService.list();
            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(categories, ExcelCategoryVo.class);
            //把数据写入到excel
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("分类导出")
                    .doWrite(excelCategoryVos);
        } catch (Exception e) {
            //如果出现异常，也要响应json数据
            ResponseResult result=ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }

    }

    @GetMapping("/list")
    public ResponseResult getCategorylist(Integer pageNum, Integer pageSize,Category category){
        return categoryService.getCategorylist(pageNum,pageSize,category);
    }

    @PostMapping
    public ResponseResult AddCategory(@RequestBody Category category){
        return categoryService.AddCategory(category);
    }

    @GetMapping("{id}")
    public ResponseResult getCategoryInfoById(@PathVariable Long id){
        return categoryService.getCategoryInfoById(id);
    }

    @PutMapping
    public ResponseResult updateCategory(@RequestBody Category category){
        categoryService.updateById(category);
        return ResponseResult.okResult();
    }

    @DeleteMapping("{id}")
    public ResponseResult deleteCategoryByid(@PathVariable Long id){
        categoryService.removeById(id);
        return ResponseResult.okResult();
    }


    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody Category category){
        categoryService.changeStatus(category);
        return ResponseResult.okResult();
    }


}
