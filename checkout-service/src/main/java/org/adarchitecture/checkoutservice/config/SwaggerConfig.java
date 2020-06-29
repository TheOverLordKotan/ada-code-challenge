/*
 * MIT License 
 * 
 * Copyright (c) 2018 ADA
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * Kotan is a Japanese word.
 * The literal meaning of this word is 'elegant simplicity'.
 * You have to admire a culture that actually has its own word mean
 * for this type of concept.
 *
 */
package org.adarchitecture.checkoutservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
/**
*
* <h1>SwaggerConfig</h1>
*
* Description
* the config documentation files
*
* @author THEOVERLORDKOTAN  (ADA)
* @version 1.0
* 
*/

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket apiDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.adarchitecture.billservice"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo());
    }

    private ApiInfo getApiInfo(){
        return new ApiInfo(
                "Code challenge follow Saga Pattern ",
                "App to demonstrate Saga Pattern using Axon and Spring Boot",
                "1.0.0",
                "Terms of Service",
                new Contact("ADA", "https://github.com/TheOverLordKotan", "ada10@ue.edu.co"),
                "",
                "",
                Collections.emptyList());
    }

}
