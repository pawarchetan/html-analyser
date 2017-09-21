package de.scout24.analyser.controller;

import de.scout24.analyser.model.WebDocument;
import de.scout24.analyser.service.HtmlAnalyserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Log4j2
public class HtmlAnalyserController {

    @Autowired
    private HtmlAnalyserService htmlAnalyserService;

    @PostMapping(value = "/analyser", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebDocument> htmlAnalyzer(@RequestBody WebDocument webDocument) throws Exception {
        log.info("Analysing Html document , URI :-- " + webDocument.getUri());
        WebDocument webDocument1 = htmlAnalyserService.analyseHtmlDocument(webDocument);
        return new ResponseEntity<>(webDocument1, HttpStatus.OK);
    }
}
