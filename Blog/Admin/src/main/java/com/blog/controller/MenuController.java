package com.blog.controller;


import com.blog.domain.ResponseResult;
import com.blog.domain.SysMenu;
import com.blog.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private SysMenuService menuService;


    @GetMapping("/list")
    public ResponseResult getSystemMenu(String status,String menuName){
        return menuService.getSystemMenu(status,menuName);
    }

    @PostMapping
    public ResponseResult addMenu(@RequestBody SysMenu menu){
        return menuService.addMenu(menu);
    }

    @GetMapping("{id}")
    public ResponseResult getMenuById(@PathVariable("id") Long id){
        return menuService.getMenuById(id);
    }

    @PutMapping
    public ResponseResult updateMenu(@RequestBody SysMenu menu){
        return menuService.updateMenu(menu);
    }

    @DeleteMapping("{id}")
    public ResponseResult deleteMenu(@PathVariable("id") Long id){
        return menuService.deleteMenu(id);
    }

    @GetMapping("/treeselect")
    public ResponseResult treeselect(){
        return menuService.treeselect();
    }


    @GetMapping("/roleMenuTreeselect/{id}")
    public ResponseResult roleMenuTreeselect(@PathVariable("id") Long id){
        return menuService.roleMenuTreeselect(id);
    }
}
