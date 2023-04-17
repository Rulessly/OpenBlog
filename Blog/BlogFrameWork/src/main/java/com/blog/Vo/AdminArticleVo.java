package com.blog.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminArticleVo {
    private List<ArticleDetailVo> rows;
    private Long total;
}