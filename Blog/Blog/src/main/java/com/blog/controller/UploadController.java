package com.blog.controller;


import com.blog.domain.ResponseResult;
import com.blog.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {

    @Autowired
    private FileService fileService;
    @PostMapping("/upload")
    public ResponseResult upload(MultipartFile img){
        return fileService.upload(img);
    }
}
