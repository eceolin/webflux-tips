package com.reactor.configuration;

import com.reactor.configuration.swagger.DocumentationPluginsManagerReplaced;
import com.reactor.configuration.swagger.TypeNameExtractorReplaced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.schema.TypeNameExtractor;
import springfox.documentation.spring.web.plugins.DocumentationPluginsManager;

@Configuration
public class OverrideSwagger {

    @Autowired private TypeNameExtractorReplaced typeNameExtractorReplaced;

    @Bean
    @Primary
    public DocumentationPluginsManager bean() {
        return new DocumentationPluginsManagerReplaced();
    }

    @Bean
    @Primary
    public TypeNameExtractor beanTypeName() {
        return typeNameExtractorReplaced;
    }
}
