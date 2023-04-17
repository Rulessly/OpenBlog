package com.blog.service;

import com.blog.domain.RoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 22410
* @description 针对表【sys_role_menu(角色和菜单关联表)】的数据库操作Service
* @createDate 2023-02-13 17:45:54
*/
public interface RoleMenuService extends IService<RoleMenu> {

    List<Long> getRoleMenuIdsById(Long id);

    void deleteRoleMenuByRoleId(Long id);
}
