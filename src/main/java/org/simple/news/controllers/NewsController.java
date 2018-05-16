package org.simple.news.controllers;

import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.simple.news.dto.NewsDto;
import org.simple.news.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/news")
@Api(value="news", description="Operations pertaining to news")
public class NewsController {

    private NewsService newsService;

    @ApiOperation(value = "News with articles", response = NewsDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved news"),
            @ApiResponse(code = 503, message = "Service temporarily unavailable"),
    })
    @GetMapping(value = "/{lang}/{category}", produces = "application/json; charset=UTF-8")
    public Future<NewsDto> getNews(
            @ApiParam(value = "Language of news", required = true)
            @PathVariable("lang") String lang,
            @ApiParam(value = "Article category", required = true)
            @PathVariable("category") String category) {
        return newsService.getNews(lang, category);
    }

}
