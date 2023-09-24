package org.com.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class WebPageControllerConfig {

    @Bean
    public ClassLoaderTemplateResolver webPageTemplateResolver(){
        ClassLoaderTemplateResolver webPageTemplateResolver = new ClassLoaderTemplateResolver();
        webPageTemplateResolver.setPrefix("templates/");
        webPageTemplateResolver.setSuffix(".html");
        webPageTemplateResolver.setTemplateMode("HTML5");
        webPageTemplateResolver.setCharacterEncoding("UTF8");
        webPageTemplateResolver.setOrder(1);
        return webPageTemplateResolver;
    }
}
