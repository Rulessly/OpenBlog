package com.blog.controller;


import com.blog.domain.Link;
import com.blog.domain.ResponseResult;
import com.blog.domain.dto.LinkDto;
import com.blog.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/link")
public class LinkController {

    @Autowired
    private LinkService linkServicel;

    @GetMapping("/list")
    public ResponseResult AdmingetLinkList(Integer pageNum, Integer pageSize, LinkDto linkDto){
        return linkServicel.AdmingetLinkList(pageNum,pageSize,linkDto);
    }

    @PostMapping
    public ResponseResult addLink(@RequestBody LinkDto linkDto){
        return linkServicel.addLink(linkDto);
    }

    @GetMapping("{id}")
    public ResponseResult getLinkInfoById(@PathVariable("id") Long id){
        return linkServicel.getLinkInfoById(id);
    }


    @PutMapping
    public ResponseResult updateLink(@RequestBody Link link){
        return linkServicel.updateLink(link);
    }

    @DeleteMapping("{id}")
    public ResponseResult deleteLinkById(@PathVariable("id") Long id){
        linkServicel.removeById(id);
        return ResponseResult.okResult();
    }
}
