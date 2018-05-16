package org.simple.news.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Data
public class ArticleDto {

    private final static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private String author;
    private String title;
    private String description;
    private LocalDateTime date;
    private String sourceName;
    private String articleUrl;
    private String imageUrl;

    @SuppressWarnings("unchecked")
    @JsonProperty("source")
    private void unpackNested(Map<String,Object> source) {
        this.sourceName = (String)source.get("name");
    }

    @JsonProperty("publishedAt")
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @JsonProperty("date")
    public String getDate() {
        if(date == null) {
            return null;
        }
        return date.format(DATE_FORMATTER);
    }

    @JsonProperty("url")
    public void setArticleUrl(String url) {
        this.articleUrl = url;
    }

    @JsonProperty("articleUrl")
    public String getArticleUrl() {
        return articleUrl;
    }

    @JsonProperty("urlToImage")
    public void setImageUrl(String urlToImage) {
        this.imageUrl = urlToImage;
    }

    @JsonProperty("imageUrl")
    public String getImageUrl() {
        return imageUrl;
    }
}