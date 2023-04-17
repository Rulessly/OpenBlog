package com.blog.mapper;

import com.blog.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author xyxiaobiao
* @description 针对表【sys_user(用户表)】的数据库操作Mapper
* @createDate 2022-11-26 17:03:15
* @Entity com.blog.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




