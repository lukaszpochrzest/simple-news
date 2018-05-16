package org.simple.news.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NewsDto {
    @ApiModelProperty(notes = "Country news are retrieved for")
    private String country;
    @ApiModelProperty(notes = "News Category")
    private String category;
    @ApiModelProperty(notes = "Articles")
    private ArticleDto[] articles;
}