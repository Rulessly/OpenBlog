package com.blog.mapper;

import com.blog.domain.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 22410
* @description 针对表【sg_tag(标签)】的数据库操作Mapper
* @createDate 2023-02-04 21:05:31
* @Entity com.blog.domain.Tag
*/
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

}




