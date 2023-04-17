package com.blog.mapper;

import com.blog.domain.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 22410
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Mapper
* @createDate 2023-02-06 21:47:26
* @Entity com.blog.domain.SysMenu
*/
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<String> selectPermsByUserId(Long id);

    List<SysMenu> selectAllRouterMenu();

    List<SysMenu> selectRouterMenuTreeByUserId(Long userId);

    List<Long> selectRoleKeyByUserId(Long id);
}




