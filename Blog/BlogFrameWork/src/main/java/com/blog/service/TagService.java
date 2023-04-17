package com.blog.service;

import com.blog.domain.ResponseResult;
import com.blog.domain.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.domain.dto.TagListDto;

/**
* @author 22410
* @description 针对表【sg_tag(标签)】的数据库操作Service
* @createDate 2023-02-04 21:05:31
*/
public interface TagService extends IService<Tag> {

    ResponseResult pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult<Tag> addTag(Tag tag);

    ResponseResult deleteTag(Long id);

    ResponseResult updateTag(Tag tag);

    ResponseResult getTag(Long id);

    ResponseResult listAllTag();
}
