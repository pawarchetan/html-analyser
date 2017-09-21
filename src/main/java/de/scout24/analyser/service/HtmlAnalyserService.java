package de.scout24.analyser.service;

import de.scout24.analyser.model.WebDocument;

public interface HtmlAnalyserService {

    WebDocument analyseHtmlDocument(WebDocument webDocument) throws Exception;

}
