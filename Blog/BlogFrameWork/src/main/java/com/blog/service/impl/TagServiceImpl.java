package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.Vo.PageVo;
import com.blog.Vo.TagUpVo;
import com.blog.Vo.TagVo;
import com.blog.domain.ResponseResult;
import com.blog.domain.Tag;
import com.blog.domain.dto.TagListDto;
import com.blog.enums.AppHttpCodeEnum;
import com.blog.service.TagService;
import com.blog.mapper.TagMapper;
import com.blog.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
* @author 22410
* @description 针对表【sg_tag(标签)】的数据库操作Service实现
* @createDate 2023-02-04 21:05:31
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService{

    @Autowired
    private TagMapper tagMapper;

    @Override
    public ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        //分页查询
        LambdaQueryWrapper<Tag> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(tagListDto.getName()),Tag::getName,tagListDto.getName());
        queryWrapper.like(StringUtils.hasText(tagListDto.getRemark()),Tag::getName,tagListDto.getRemark());

        Page<Tag> page=new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,queryWrapper);
        // 封装数据返回
        PageVo pageV=new PageVo(page.getRecords(),page.getTotal());
        return ResponseResult.okResult(pageV);
    }

    @Override
    public ResponseResult<Tag> addTag(Tag tag) {
        //查询是否有重复的标签
        LambdaQueryWrapper<Tag> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Tag::getName,tag.getName());

        Tag one = getOne(queryWrapper);
        if(one !=null){
            return ResponseResult.errorResult(AppHttpCodeEnum.TAG_EXIST);
        }
        //判断标签是否为空
        if(Objects.isNull(tag.getName())){
            return ResponseResult.errorResult(AppHttpCodeEnum.TAGID_NONULL);
        }
        tagMapper.insert(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteTag(Long id) {
        tagMapper.deleteById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult updateTag(Tag tag) {
        //查询更新的标签是否存在
        LambdaQueryWrapper<Tag> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Tag::getName,tag.getName());
        Tag one = getOne(queryWrapper);
        if(one!=null){
            return ResponseResult.errorResult(AppHttpCodeEnum.TAG_EXIST);
        }
        //判断标签是否为空
        if(Objects.isNull(tag.getName())){
            return ResponseResult.errorResult(AppHttpCodeEnum.TAGID_NONULL);
        }

//        //        2.将TagDto对象转换为Tag对象
//        Tag tag = BeanCopyUtils.copyBean(tagDto, Tag.class);
        updateById(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getTag(Long id) {
        Tag tag = getById(id);
        if(Objects.isNull(tag)){
            return ResponseResult.errorResult(AppHttpCodeEnum.TAGID_NOEXIST);
        }
       // 将tag转换为tagUpvo
        TagUpVo tagUpVo = BeanCopyUtils.copyBean(tag, TagUpVo.class);
        tagMapper.selectById(id);
        return ResponseResult.okResult(tag);
    }

    @Override
    public ResponseResult listAllTag() {
        //查询所有标签
        LambdaQueryWrapper<Tag> queryWrapper=new LambdaQueryWrapper<>();
        List<Tag> tagList = tagMapper.selectList(queryWrapper);
        //将tagList转换为TagVo对象封装返回
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(tagList, TagVo.class);
        return ResponseResult.okResult(tagVos);
    }


}




