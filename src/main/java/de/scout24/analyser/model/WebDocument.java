package de.scout24.analyser.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
public class WebDocument {
    private String uri;
    private String htmlVersion;
    private String title;
    private Map<String, Integer> headingTagMap = new HashMap<>();
    private int numOfInternalLinks;
    private int numOfExternalLinks;
    private boolean hasLoginForm;
    private Map<String, Integer> linkResourceValidationMap = new HashMap<>();
}
