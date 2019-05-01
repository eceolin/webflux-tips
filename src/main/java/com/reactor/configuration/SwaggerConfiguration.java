package com.reactor.configuration;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;

import java.util.Collection;

@Configuration
@EnableSwagger2WebFlux
public class SwaggerConfiguration {

    @Bean
    public Docket api(final TypeResolver typeResolver) {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .alternateTypeRules(getRules(typeResolver));
    }

    private AlternateTypeRule[] getRules(TypeResolver typeResolver){
        return new AlternateTypeRule[]{AlternateTypeRules.newRule(
                typeResolver.resolve(Flux.class, WildcardType.class),
                typeResolver.resolve(Collection.class, WildcardType.class)),
                AlternateTypeRules.newRule(
                        typeResolver.resolve(Flux.class,
                                typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
                        typeResolver.resolve(Collection.class, WildcardType.class)),
                AlternateTypeRules.newRule(
                        typeResolver.resolve(Mono.class,
                                typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
                        typeResolver.resolve(WildcardType.class)),
                AlternateTypeRules.newRule(
                        typeResolver.resolve(Mono.class, WildcardType.class),
                        typeResolver.resolve(WildcardType.class))};
    }
}