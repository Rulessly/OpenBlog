package com.blog.service;

import com.blog.domain.ResponseResult;
import com.blog.domain.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 22410
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Service
* @createDate 2023-02-06 21:47:26
*/
public interface SysMenuService extends IService<SysMenu> {

    List<String> selectPermsByUserId(Long id);

    List<SysMenu> selectRouterMenuTreeByUserId(Long userId);

    ResponseResult getSystemMenu(String status, String menuName);

    ResponseResult addMenu(SysMenu menu);

    ResponseResult getMenuById(Long id);

    ResponseResult updateMenu(SysMenu menu);

    ResponseResult deleteMenu(Long id);

    ResponseResult treeselect();
    List<SysMenu> selectMenuList(SysMenu menu);

    ResponseResult roleMenuTreeselect(Long id);
}
