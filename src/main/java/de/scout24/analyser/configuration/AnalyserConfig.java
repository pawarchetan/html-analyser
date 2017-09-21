package de.scout24.analyser.configuration;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnalyserConfig {

    @Bean
    public UrlValidator urlValidator(){
        String[] schemes = {"http","https"};
        return new UrlValidator(schemes);
    }
}
