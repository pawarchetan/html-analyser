package de.scout24.analyser.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.scout24.analyser.model.WebDocument;
import de.scout24.analyser.service.HtmlAnalyserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(HtmlAnalyserController.class)
public class HtmlAnalyserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HtmlAnalyserService htmlAnalyserService;

    @Test
    public void postUrlToAnalyse() throws Exception {
        WebDocument webDocument = getWebDocument();
        String webDocumentString = convertObjectToJsonBytes(webDocument);

        Mockito.when(htmlAnalyserService.analyseHtmlDocument(webDocument)).thenReturn(webDocument);
        mockMvc.perform(post("/api/analyser")
                .header("Origin", "http://localhost:8080")
                .accept("application/json")
                .contentType("application/json")
                .content(webDocumentString))
                .andExpect(status().isOk());
    }

    private WebDocument getWebDocument(){
        WebDocument webDocument = new WebDocument();
        webDocument.setUri("https://bitbucket.org/account/signin/");
        return webDocument;
    }

    private String convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

}
