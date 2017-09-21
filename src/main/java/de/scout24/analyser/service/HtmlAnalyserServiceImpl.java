package de.scout24.analyser.service;

import com.google.common.collect.Lists;
import de.scout24.analyser.exception.InvalidUrlException;
import de.scout24.analyser.model.WebDocument;
import de.scout24.analyser.thread.ResourceValidation;
import de.scout24.analyser.util.HtmlParserUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

@Service
@Log4j2
public class HtmlAnalyserServiceImpl implements HtmlAnalyserService {

    @Autowired
    private UrlValidator urlValidator;

    @Override
    public WebDocument analyseHtmlDocument(WebDocument webDocument) throws Exception {
        boolean isValidUrl = urlValidator.isValid(webDocument.getUri());
        if (!isValidUrl) {
            log.error("Invalid URI : " + webDocument.getUri());
            throw new InvalidUrlException("Invalid URL : " + webDocument.getUri());
        } else {
            try {
                Document doc = Jsoup.connect(webDocument.getUri()).get();

                List<String> hyperLinksCollection = HtmlParserUtil.getHyperLinksCollection(doc);
                int[] numOfLinks = HtmlParserUtil.getNumOfHyperMediaLink(doc, hyperLinksCollection);

                webDocument.setHtmlVersion(HtmlParserUtil.getHtmlDocType(doc));
                webDocument.setTitle(HtmlParserUtil.getTitle(doc));
                webDocument.setHeadingTagMap(HtmlParserUtil.getNumOfHeadingsByGroup(doc));
                webDocument.setNumOfInternalLinks(numOfLinks[0]);
                webDocument.setNumOfExternalLinks(numOfLinks[1]);
                webDocument.setHasLoginForm(HtmlParserUtil.hasLogin(doc));
                webDocument.setLinkResourceValidationMap(runResourceValidation(hyperLinksCollection));

            } catch (IOException e) {
                log.error("Exception while analysing Html document ", e);
            }
        }
        return webDocument;
    }

    private Map<String, Integer> runResourceValidation(List<String> resourceList) {

        Map<String, Integer> resourceValidationMap = new HashMap<>();

        final int numOfThread = 9; //thread pool size set to (No. CPU + 1) is optimal on average.
        ExecutorService threadPool = Executors.newFixedThreadPool(numOfThread);

        List<FutureTask<Map<String, Integer>>> futureTaskList = new ArrayList<>();

        List<List<String>> subSets = Lists.partition(resourceList, numOfThread);

        for (List<String> subList: subSets) {
            ResourceValidation task = new ResourceValidation(subList);
            FutureTask<Map<String, Integer>> futureTask = new FutureTask<>(task);
            threadPool.submit(futureTask);
            futureTaskList.add(futureTask);
        }

        for (FutureTask<Map<String, Integer>> completeFutureTask: futureTaskList) {
            try {
                resourceValidationMap.putAll(completeFutureTask.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        threadPool.shutdown();
        return resourceValidationMap;
    }
}
