package com.blog.service;

import com.blog.Vo.RoleVo;
import com.blog.domain.ResponseResult;
import com.blog.domain.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.domain.dto.RoleDto;

import java.util.List;

/**
* @author 22410
* @description 针对表【sys_role(角色信息表)】的数据库操作Service
* @createDate 2023-02-06 22:58:43
*/
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);

    ResponseResult getRoleList(Integer pageNum, Integer pageSize, RoleVo roleVo);


    ResponseResult addRole(Role role);

    ResponseResult UpdateRole(Role roleDto);

    ResponseResult DeleteRole(Long id);

    ResponseResult listAllRole();

    List<Role> selectRoleAll();

    List<Long> selectRoleIdByUserId(Long userId);
}
