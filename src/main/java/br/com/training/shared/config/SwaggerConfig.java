package br.com.training.shared.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    public static final String User = "User";

    @Bean
    public Docket apiUser() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .groupName("v1")
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.training.v1.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(this.metaInfo())
                .tags(this.tag());
    }

    private Tag tag() {
        return new Tag(
                User,
                "Operações referentes a manipulação de entidade User.");
    }

    private ApiInfo metaInfo() {
        return new ApiInfoBuilder()
                .title("Api Training Java")
                .description("Api responsável pela manutenção de usuários")
                .version("1.0.0")
                .license("Apache License Version 2.0")
                .licenseUrl("https://github.com/raphaelfeitosa/java-training-api/blob/main/LICENSE")
                .contact(this.contact())
                .build();
    }

    private Contact contact() {
        return new Contact(
                "Raphael Feitosa",
                "https://www.linkedin.com/in/raphael-feitosa/",
                "raphaelcs2@gmail.com");
    }

}
