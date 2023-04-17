package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.Vo.MenuTreeVo;
import com.blog.Vo.RoleMenuTreeSelectVo;
import com.blog.contants.SystemConstants;
import com.blog.domain.ResponseResult;
import com.blog.domain.SysMenu;
import com.blog.enums.AppHttpCodeEnum;
import com.blog.service.SysMenuService;
import com.blog.mapper.SysMenuMapper;
import com.blog.utils.BeanCopyUtils;
import com.blog.utils.SecurityUtils;
import com.blog.utils.SystemConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.blog.enums.AppHttpCodeEnum.*;

/**
* @author 22410
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Service实现
* @createDate 2023-02-06 21:47:26
*/
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu>
    implements SysMenuService{

    @Override
    public List<String> selectPermsByUserId(Long id) {
        //如果是管理员，返回所有的权限
        if(SecurityUtils.isAdmin()){
            LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(SysMenu::getMenuType, SystemConstants.MENU,SystemConstants.BUTTON);
            wrapper.eq(SysMenu::getStatus,SystemConstants.STATUS_NORMAL);
            List<SysMenu> menus = list(wrapper);
            List<String> perms = menus.stream()
                    .map(SysMenu::getPerms)
                    .collect(Collectors.toList());
            return perms;
        }
        //否则返回所具有的权限
        return getBaseMapper().selectPermsByUserId(id);
    }

    @Override
    public List<SysMenu> selectRouterMenuTreeByUserId(Long userId) {
        SysMenuMapper menuMapper = getBaseMapper();
        List<SysMenu> menus = null;
        //判断是否是管理员
        if(SecurityUtils.isAdmin()){
            //如果是 获取所有符合要求的Menu
            menus = menuMapper.selectAllRouterMenu();
        }else{
            //否则  获取当前用户所具有的Menu
            menus = menuMapper.selectRouterMenuTreeByUserId(userId);
        }

        //构建tree
        //先找出第一层的菜单  然后去找他们的子菜单设置到children属性中
        List<SysMenu> menuTree = builderMenuTree(menus,0L);
        return menuTree;
    }

    @Override
    public ResponseResult getSystemMenu(String status, String menuName) {
        //查询
        LambdaQueryWrapper<SysMenu> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(status),SysMenu::getStatus,status)
                .like(StringUtils.hasText(menuName),SysMenu::getMenuName,menuName)
                .orderByAsc(SysMenu::getParentId,SysMenu::getOrderNum);
        List<SysMenu> menuList = list(queryWrapper);

        return ResponseResult.okResult(menuList);
    }

    @Override
    public ResponseResult addMenu(SysMenu menu) {
        //判断是否有重复的菜单名
        LambdaQueryWrapper<SysMenu> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(SysMenu::getMenuName,menu.getMenuName());
        SysMenu one = getOne(queryWrapper);
        if(one!=null){
            return ResponseResult.errorResult(AppHttpCodeEnum.MENUNAME_EXIST);
        }
        save(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getMenuById(Long id) {
        SysMenu menu = getById(id);
        return ResponseResult.okResult(menu);
    }

    @Override
    public ResponseResult updateMenu(SysMenu menu) {
//        LambdaQueryWrapper<SysMenu> queryWrapper=new LambdaQueryWrapper<>();
//        queryWrapper.eq(SysMenu::getMenuName,menu.getMenuType());
//        SysMenu one = getOne(queryWrapper);
//        if(one.getMenuName().equals(menu.getMenuName())){
//            return ResponseResult.errorResult(MENUNAME_NO_HAS_SAME);
//        }
//        if(menu==null){
//            return ResponseResult.errorResult(CONTENT_NOT_NULL);
//        }
        //        1.判断对象值是否为空
        if (!StringUtils.hasText(menu.getMenuName()) ||
                !StringUtils.hasText(menu.getMenuType()) ||
                !StringUtils.hasText(String.valueOf(menu.getStatus())) ||
                !StringUtils.hasText(menu.getPath()) ||
                !StringUtils.hasText(String.valueOf(menu.getOrderNum())) ||
                !StringUtils.hasText(menu.getIcon())) {
            return ResponseResult.errorResult(CONTENT_NOT_NULL);
        }
        if (menu.getId().equals(menu.getParentId())) {
            return ResponseResult.errorResult(500,"修改菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己");
        }
        save(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteMenu(Long id) {
        //查询是否有子菜单
        LambdaQueryWrapper<SysMenu> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(SysMenu::getParentId,id);
        List<SysMenu> menuList = getBaseMapper().selectList(queryWrapper);
        if(!menuList.isEmpty()&&menuList.size()!=0){
            return ResponseResult.errorResult(DELETE_MENU_FALSE);
        }
        removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult treeselect() {
        // 复用之前的selectMenuList方法。方法需要参数，参数可以用来进行条件查询，而这个方法不需要条件，所以直接new Menu()传入
        List<SysMenu> menus = selectMenuList(new SysMenu());
        List<MenuTreeVo> menuTreeVos = SystemConverter.buildMenuSelectTree(menus);
        return ResponseResult.okResult(menuTreeVos);
    }

    @Override
    public List<SysMenu> selectMenuList(SysMenu menu) {
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        //menuName模糊查询
        queryWrapper.like(StringUtils.hasText(menu.getMenuName()),SysMenu::getMenuName,menu.getMenuName());
        queryWrapper.eq(StringUtils.hasText(menu.getStatus()),SysMenu::getStatus,menu.getStatus());
        //排序 parent_id和order_num
        queryWrapper.orderByAsc(SysMenu::getParentId,SysMenu::getOrderNum);
        List<SysMenu> menus = list(queryWrapper);;
        return menus;
    }

    @Override
    public ResponseResult roleMenuTreeselect(Long id) {
//        List<SysMenu> menus =selectMenuList(new SysMenu());
//        List<Long> checkedKeys = this.selectMenuListByRoleId(id);
//        List<MenuTreeVo> menuTreeVos = SystemConverter.buildMenuSelectTree(menus);
//        RoleMenuTreeSelectVo vo = new RoleMenuTreeSelectVo(menuTreeVos);
//        return ResponseResult.okResult(vo);
        List<SysMenu> menus = selectMenuList(new SysMenu());
        List<Long> checkedKeys =selectMenuListByRoleId(id);
        List<MenuTreeVo> menuTreeVos = SystemConverter.buildMenuSelectTree(menus);
        RoleMenuTreeSelectVo vo = new RoleMenuTreeSelectVo(checkedKeys,menuTreeVos);
        return ResponseResult.okResult(vo);
    }


    /**
     * 根据id查询对应用户的角色信息
     * @param id
     * @return
     */
    private List<Long> selectMenuListByRoleId(Long id) {
        return getBaseMapper().selectRoleKeyByUserId(id);
    }

    private List<SysMenu> builderMenuTree(List<SysMenu> menus, Long parentId) {
        List<SysMenu> menuTree = menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());
        return menuTree;
    }

    /**
     * 获取存入参数的 子Menu集合
     * @param menu
     * @param menus
     * @return
     */
    private List<SysMenu> getChildren(SysMenu menu, List<SysMenu> menus) {
        List<SysMenu> childrenList = menus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .map(m->m.setChildren(getChildren(m,menus)))
                .collect(Collectors.toList());
        return childrenList;
    }
}




