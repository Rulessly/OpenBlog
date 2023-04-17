package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.Vo.PageVo;
import com.blog.Vo.RoleVo;
import com.blog.contants.SystemConstants;
import com.blog.domain.ResponseResult;
import com.blog.domain.Role;
import com.blog.domain.RoleMenu;
import com.blog.domain.dto.RoleDto;
import com.blog.service.RoleMenuService;
import com.blog.service.RoleService;
import com.blog.mapper.RoleMapper;
import com.blog.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.blog.enums.AppHttpCodeEnum.*;

/**
* @author 22410
* @description 针对表【sys_role(角色信息表)】的数据库操作Service实现
* @createDate 2023-02-06 22:58:43
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

    @Autowired
    private RoleMenuService roleMenuServicel;

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        //判断是否是管理员 如果是，返回集合中只需要有admin
        if(id==1L){
            List<String> roleKeys=new ArrayList<>();
            roleKeys.add("admin");
            return  roleKeys;
        }
        //否则查询当前用户所预留的角色信息
       return getBaseMapper().selectRoleKeyByUserId(id);
    }

    @Override
    public ResponseResult getRoleList(Integer pageNum, Integer pageSize, RoleVo roleVo) {

        //查询角色
        LambdaQueryWrapper<Role> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(roleVo.getStatus()), Role::getStatus,roleVo.getStatus());
        queryWrapper.like(StringUtils.hasText(roleVo.getRoleName()), Role::getRoleName,roleVo.getRoleName());
        //分页查询
        Page<Role> page=new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        long total = page.getTotal();
        List<Role> records = page.getRecords();
        List<Role> roles = BeanCopyUtils.copyBeanList(records, Role.class);
        PageVo pageVo=new PageVo(roles,total);
        return ResponseResult.okResult(pageVo);
    }

    @Override
    @Transactional
    public ResponseResult addRole(Role role) {
//        if(!judgeRoleByName(roleDto.getRoleName())){
//            return ResponseResult.errorResult(ROLE_EXIST);
//        }
//        if(!judgeRoleByKey(roleDto.getRoleKey())){
//            return ResponseResult.errorResult(ROLEKEY_EXIST);
//        }
//
//        //获取当前角色菜单列表
//        List<Long> menuIds = roleDto.getMenuIds();
//        Role role = BeanCopyUtils.copyBean(roleDto, Role.class);
//        save(role);
//
//        //        4.根据角色名获取到当前角色
//        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(Role::getRoleName, role.getRoleName());
//        Role getRole = getOne(queryWrapper);
//
//       // 5.遍历menuIds，添加到sys_role_menu表中
//        menuIds.stream()
//                .map(menuId -> roleMenuServicel.save
//                        (new RoleMenu(getRole.getId(), menuId)));
//        return ResponseResult.okResult();
        save(role);
        System.out.println(role.getId());
        if(role.getMenuIds()!=null&&role.getMenuIds().length>0){
            insertRoleMenu(role);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult UpdateRole(Role role) {
        updateById(role);
        roleMenuServicel.deleteRoleMenuByRoleId(role.getId());
        insertRoleMenu(role);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult DeleteRole(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllRole() {
        return ResponseResult.okResult(list());
    }

    @Override
    public List<Role> selectRoleAll() {
        LambdaQueryWrapper<Role> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getStatus, SystemConstants.STATUS_NORMAL);
        List<Role> list = list(queryWrapper);
        return list;
    }

    @Override
    public List<Long> selectRoleIdByUserId(Long userId) {
        return getBaseMapper().selectRoleIdByUserId(userId);
    }

    private void insertRoleMenu(Role role) {
        List<RoleMenu> roleMenuList = Arrays.stream(role.getMenuIds())
                .map(memuId -> new RoleMenu(role.getId(), memuId))
                .collect(Collectors.toList());
        roleMenuServicel.saveBatch(roleMenuList);
    }


    public boolean judgeRoleByName(String name){
        LambdaQueryWrapper<Role> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getRoleName,name);
        Role one = getOne(queryWrapper);
        if(Objects.isNull(one)){
            return true;
        }else {
            return false;
        }
    }


    public boolean judgeRoleByKey(String key){
        LambdaQueryWrapper<Role> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getRoleKey,key);
        Role one = getOne(queryWrapper);
        if(Objects.isNull(one)){
            return true;
        }else {
            return false;
        }
    }


}




