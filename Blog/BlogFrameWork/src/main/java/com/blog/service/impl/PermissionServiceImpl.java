package com.blog.service.impl;


import com.blog.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ps")
public class PermissionServiceImpl {

    /**
     * 判断当前用户是否具有permission
     * @param permission
     * @return
     */
    public boolean hasPermisson(String permission){

        //如果是超级管理员  直接返回true
        if(SecurityUtils.isAdmin()){
            return true;
        }
        //否则  获取当前登录用户所具有的权限列表 如何判断是否存在permission
        List<String> permissions = SecurityUtils.getLoginUser().getPermissions();
        return permissions.contains(permission);
    }
}
