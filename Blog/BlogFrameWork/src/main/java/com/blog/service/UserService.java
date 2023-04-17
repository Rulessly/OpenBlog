package com.blog.service;

import com.blog.domain.ResponseResult;
import com.blog.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.domain.dto.ChangeUserStatusDto;
import com.blog.domain.dto.UserDto;

/**
* @author xyxiaobiao
* @description 针对表【sys_user(用户表)】的数据库操作Service
* @createDate 2022-11-26 17:03:15
*/
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);


    ResponseResult getUserlist(Integer pageNum, Integer pageSize, UserDto userDto);

    ResponseResult deleteUser(Long id);

    ResponseResult addUser(User user);

    ResponseResult getUserInfo(Long id);

    ResponseResult updateUserInfoByAdmin(User user);

    ResponseResult changeStatus(ChangeUserStatusDto changeUserStatusDto);
}
