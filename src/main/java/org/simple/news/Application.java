package org.simple.news;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.simple.news.exception.RestTemplateExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@SpringBootApplication
@ComponentScan
@EnableWebMvc
public class Application extends WebMvcConfigurationSupport {

    @Bean
    public AsyncRestTemplate asyncRestTemplate() {
        AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();
        asyncRestTemplate.setErrorHandler(new RestTemplateExceptionHandler());
        return asyncRestTemplate;
    }

    @Autowired
    public void configureObjectMapper(ObjectMapper objectMapper) {
        objectMapper.registerModule(new JavaTimeModule());
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
