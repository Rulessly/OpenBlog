package com.blog.service;

import com.blog.domain.ResponseResult;
import com.blog.domain.User;

public interface BlogLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
