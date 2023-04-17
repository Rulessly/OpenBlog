package com.blog.controller;


import com.blog.Vo.RoleVo;
import com.blog.domain.ResponseResult;
import com.blog.domain.Role;
import com.blog.domain.dto.ChangeRoleStatusDto;
import com.blog.domain.dto.RoleDto;
import com.blog.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;


    @GetMapping("/list")
    public ResponseResult getRoleList(Integer pageNum, Integer pageSize, RoleVo roleVo){
        return roleService.getRoleList(pageNum,pageSize,roleVo);
    }

    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody ChangeRoleStatusDto changeRoleStatusDto){
        Role role = new Role();
        role.setId(changeRoleStatusDto.getRoleId());
        role.setStatus(changeRoleStatusDto.getStatus());
        roleService.updateById(role);
        return ResponseResult.okResult();
    }

    @PostMapping
    public ResponseResult addRole(@RequestBody Role role){
        return roleService.addRole(role);
    }

    @GetMapping("{id}")
    public ResponseResult getRoleInfo(@PathVariable("id") Long id){
        Role byId = roleService.getById(id);
        return ResponseResult.okResult(byId);
    }


    @PutMapping
    public ResponseResult UpdateRole(@RequestBody Role role){
        return roleService.UpdateRole(role);
    }


    @DeleteMapping("{id}")
    public ResponseResult DeleteRole(@PathVariable("id") Long id){
        return roleService.DeleteRole(id);
    }

    @GetMapping("/listAllRole")
    public ResponseResult listAllRole(){
        return roleService.listAllRole();
    }
}
