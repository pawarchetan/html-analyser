package de.scout24.analyser.util;

import lombok.extern.log4j.Log4j2;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@Log4j2
public class HtmlParserUtil {

    public static String getTitle(Document doc) {

        String title = "Title is not set";
        if (doc.title() != null) {
            title = doc.title();
        }

        log.info("Title :-- " + title);
        return title;
    }

    public static String getHtmlDocType(Document doc) {

        String docType = "";
        List<Node> nods = doc.childNodes();
        for (Node node : nods) {
            if (node instanceof DocumentType) {
                DocumentType documentType = (DocumentType) node;
                docType = documentType.toString();
                break;
            }
        }

        log.info("Html Doc Type :-- " + docType);
        return docType;
    }

    public static Map<String, Integer> getNumOfHeadingsByGroup(Document doc) {

        Map<String, Integer> hTagsMap = new HashMap<>();
        Elements hTags = doc.select("h1, h2, h3, h4, h5, h6");
        hTagsMap.put("h1", hTags.select("h1").size());
        hTagsMap.put("h2", hTags.select("h2").size());
        hTagsMap.put("h3", hTags.select("h3").size());
        hTagsMap.put("h4", hTags.select("h4").size());
        hTagsMap.put("h5", hTags.select("h5").size());
        hTagsMap.put("h6", hTags.select("h6").size());

        log.info("Heading groups :-- " + hTagsMap);
        return hTagsMap;
    }

    public static List<String> getHyperLinksCollection(Document doc) {

        List<String> hyperLinksCollection = new ArrayList<>();
        Elements links = doc.select("a[href]");

        for (Element link : links) {
            String href = link.attr("abs:href");
            if (null != href)
                hyperLinksCollection.add(href);
        }

        log.info("HyperLinks :-- " + hyperLinksCollection);
        return hyperLinksCollection;
    }

    public static int[] getNumOfHyperMediaLink(Document doc, List<String> hyperlinkCollection) {
        int[] numOfLinks = new int[2];
        int internalLinks = 0;
        int externalLinks = 0;

        try {
            URL aURL = new URL(doc.baseUri());
            String domain = aURL.getHost();
            for (String link: hyperlinkCollection) {
                if (link.contains(domain))
                    internalLinks++;
                else
                    externalLinks++;
            }
            numOfLinks[0] = internalLinks;
            numOfLinks[1] = externalLinks;

        } catch (MalformedURLException e) {
            log.error("URL : " + doc.baseUri() + "is Malformed." + e);
        }

        log.info("Number of Hyper Media Links :-- " + Arrays.toString(numOfLinks));
        return numOfLinks;
    }

    public static boolean hasLogin(Document doc) {

        boolean hasLoginForm = false;
        Elements inputs = doc.getElementsByTag("input");

        for (Element element : inputs) {
            String password = element.attr("type");
            if (null != password && password.equalsIgnoreCase("password")) {
                hasLoginForm = true;
                break;
            }
        }

        log.info("hasLoginForm :-- " + hasLoginForm);
        return hasLoginForm;
    }
}
