package org.simple.news.service;

import org.simple.news.dto.NewsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureAdapter;

import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

class NewsFutureAdapter extends ListenableFutureAdapter<NewsDto, ResponseEntity<NewsDto>> {

    private final String country;
    private final String category;

    public NewsFutureAdapter(String lang, String category, ListenableFuture<ResponseEntity<NewsDto>> newsDto) {
        super(newsDto);
        this.country = getCountryFor(lang);
        this.category = isValidCategory(category) ? category : null;
    }

    @Override
    protected NewsDto adapt(ResponseEntity<NewsDto> responseEntity) throws ExecutionException {
        NewsDto newsDto = responseEntity.getBody();
        newsDto.setCountry(country);
        newsDto.setCategory(category);
        return newsDto;
    }

    private String getCountryFor(String langToFindCountryFor) {
        return Arrays.stream(Locale.getISOCountries())
                .filter(country -> country.toLowerCase().equals(langToFindCountryFor))
                .findFirst()
                .map(country -> new Locale("", country).getDisplayCountry())
                .orElse(null);
    }

    private boolean isValidCategory(String category) {
        return Arrays.stream(Category.values())
                .anyMatch(validCategory -> validCategory.category().equals(category));
    }

}