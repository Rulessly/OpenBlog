package com.blog.controller;

import com.blog.Vo.AdmainUserInfo;
import com.blog.Vo.RouterVo;
import com.blog.Vo.UserInfo;
import com.blog.domain.LoginUser;
import com.blog.domain.ResponseResult;
import com.blog.domain.SysMenu;
import com.blog.domain.User;
import com.blog.enums.AppHttpCodeEnum;
import com.blog.exception.SystemException;
import com.blog.service.LoginService;
import com.blog.service.RoleService;
import com.blog.service.SysMenuService;
import com.blog.utils.BeanCopyUtils;
import com.blog.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin
public class LoginController {


    @Autowired
    private LoginService loginService;

    @Autowired
    private SysMenuService sysMenuService;


    @Autowired
    private RoleService roleService;
    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())){
            //提示必须传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }

        return  loginService.login(user);
    }


    @GetMapping("getInfo")
    public ResponseResult<AdmainUserInfo> getInfo(){
        //获取当前登录用户信息
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //根据用户id查询权限信息
        List<String> perms=sysMenuService.selectPermsByUserId(loginUser.getUser().getId());
        //根据用户id查询角色信息
        List<String> roleKeyList=roleService.selectRoleKeyByUserId(loginUser.getUser().getId());

        //获取用户信息
        User user = loginUser.getUser();
        UserInfo userInfo = BeanCopyUtils.copyBean(user, UserInfo.class);


        //封装vo返回
        AdmainUserInfo admainUserInfo=new AdmainUserInfo(perms,roleKeyList,userInfo);
        return ResponseResult.okResult(admainUserInfo);
    }


    @GetMapping("/getRouters")
    public ResponseResult<RouterVo> getRouter(){
        Long userId = SecurityUtils.getUserId();
        //查询menu 结果是tree的形式
        List<SysMenu> menus = sysMenuService.selectRouterMenuTreeByUserId(userId);
        //封装数据返回
        return ResponseResult.okResult(new RouterVo(menus));
    }


    @PostMapping("/user/logout")
    public ResponseResult logout(){
        return loginService.logout();
    }

}
