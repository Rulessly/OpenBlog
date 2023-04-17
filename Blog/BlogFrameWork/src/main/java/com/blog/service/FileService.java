package com.blog.service;

import com.blog.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    //文件上传
    ResponseResult upload(MultipartFile file);
}
