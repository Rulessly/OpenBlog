package com.blog.mapper;

import com.blog.domain.Link;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author xyxiaobiao
* @description 针对表【sg_link(友链)】的数据库操作Mapper
* @createDate 2022-11-26 16:38:04
* @Entity com.blog.domain.Link
*/

@Mapper
public interface LinkMapper extends BaseMapper<Link> {

}




