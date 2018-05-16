package org.simple.news.service;

import org.simple.news.dto.NewsDto;

import java.util.concurrent.Future;

public interface NewsService {

    Future<NewsDto> getNews(String lang, String category);

}