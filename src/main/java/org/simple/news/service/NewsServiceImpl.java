package org.simple.news.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.simple.news.dto.NewsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.concurrent.Future;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
@Slf4j
public class NewsServiceImpl implements NewsService {

    private final AsyncRestTemplate asyncRestTemplate;

    @Value("${newsapi.url}")
    private String url;

    @Value("${newsapi.apikey}")
    private String apiKey;

    @Value("${newsapi.apikey-param}")
    private String apiKeyParam;

    @Value("${newsapi.country-param}")
    private String countryParam;

    @Value("${newsapi.category-param}")
    private String categoryParam;

    @Override
    public Future<NewsDto> getNews(String lang, String category) {
        HttpEntity<?> entity = new HttpEntity<>(new HttpHeaders());

        String uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam(apiKeyParam, apiKey)
                .queryParam(countryParam, lang)
                .queryParam(categoryParam, category)
                .toUriString();

        ListenableFuture<ResponseEntity<NewsDto>> newsFuture = asyncRestTemplate.exchange(
                uri,
                HttpMethod.GET,
                entity,
                NewsDto.class
        );

        return new NewsFutureAdapter(lang, category, newsFuture);
    }
}