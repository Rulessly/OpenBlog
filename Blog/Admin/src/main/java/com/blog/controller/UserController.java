package com.blog.controller;


import com.blog.domain.ResponseResult;
import com.blog.domain.User;
import com.blog.domain.dto.ChangeUserStatusDto;
import com.blog.domain.dto.UserDto;
import com.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public ResponseResult getUserlist(Integer pageNum, Integer pageSize, UserDto userDto){
        return userService.getUserlist(pageNum,pageSize,userDto);
    }


    @PostMapping
    public ResponseResult addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    @DeleteMapping("{id}")
    public ResponseResult deleteUser(@PathVariable("id") Long id){
        return userService.deleteUser(id);
    }


    @GetMapping("{id}")
    public ResponseResult getUserInfo(@PathVariable("id") Long id){
        return userService.getUserInfo(id);
    }

    @PutMapping
    public ResponseResult updateUserInfoByAdmin(@RequestBody User user){
        return userService.updateUserInfoByAdmin(user);
    }

    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody ChangeUserStatusDto changeUserStatusDto){
        return userService.changeStatus(changeUserStatusDto);
    }
}
