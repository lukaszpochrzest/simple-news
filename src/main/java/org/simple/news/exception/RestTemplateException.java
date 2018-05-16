package org.simple.news.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class RestTemplateException extends RuntimeException {

    private HttpStatus errorCode;

}
