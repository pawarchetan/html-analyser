package de.scout24.analyser.controller;

import de.scout24.analyser.exception.HtmlAnalyserError;
import de.scout24.analyser.exception.InvalidUrlException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DefaultControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidUrlException.class)
    public ResponseEntity<HtmlAnalyserError> handleInvalidUrlException(){
        HtmlAnalyserError htmlAnalyserError = new HtmlAnalyserError();
        htmlAnalyserError.setMessage("Invalid URL");
        htmlAnalyserError.setStatusCode(HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(htmlAnalyserError, HttpStatus.BAD_REQUEST);
    }

}
