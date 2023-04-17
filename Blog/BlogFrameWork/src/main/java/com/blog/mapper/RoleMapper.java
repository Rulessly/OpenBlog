package com.blog.mapper;

import com.blog.domain.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 22410
* @description 针对表【sys_role(角色信息表)】的数据库操作Mapper
* @createDate 2023-02-06 22:58:43
* @Entity com.blog.domain.Role
*/
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeyByUserId(Long id);

    List<Long> selectRoleIdByUserId(Long userId);
}




