package de.scout24.analyser.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class HtmlParserUtilTest {
    private Document documentLogin;

    @Before
    public void setup() {
        ClassLoader classLoader = getClass().getClassLoader();
        File inputWithLogin = new File(classLoader.getResource("test.html").getFile());
        try {
            documentLogin = Jsoup.parse(inputWithLogin, "UTF-8", "https://bitbucket.org/account/signin/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetTitle() {
        assertEquals("failure - strings are not equal",
                HtmlParserUtil.getTitle(documentLogin), "Bitbucket");
    }

    @Test
    public void testGetHtmlDocType() {
        assertEquals("failure - strings are not equal",
                HtmlParserUtil.getHtmlDocType(documentLogin), "<!doctype html>");
    }

    @Test
    public void testGetNumOfHeadingsByGroup() {
        assertEquals("failure - strings are not equal",
                HtmlParserUtil.getNumOfHeadingsByGroup(documentLogin).toString(), "{h1=1, h2=0, h3=0, h4=0, h5=0, h6=0}");
    }

    @Test
    public void testGetHyperLinksCollection() {
        assertEquals("failure - numbers are not equal",
                HtmlParserUtil.getHyperLinksCollection(documentLogin).size(), 18);
    }

    @Test
    public void testGetNumOfHyperMediaLink() {
        List<String> links = HtmlParserUtil.getHyperLinksCollection(documentLogin);
        assertEquals("failure - numbers are not equal",
                HtmlParserUtil.getNumOfHyperMediaLink(documentLogin, links)[0], 15);
        assertEquals("failure - numbers are not equal",
                HtmlParserUtil.getNumOfHyperMediaLink(documentLogin, links)[1], 3);
    }

    @Test
    public void testHasLoginFalse() {
        assertEquals("failure - did not match",
                HtmlParserUtil.hasLogin(documentLogin), true);
    }
}
