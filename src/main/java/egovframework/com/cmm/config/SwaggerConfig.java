package egovframework.com.cmm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;


@Configuration
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfig {

    @Bean
    public Docket newsApiAll() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("00. All Device API REST Service")
                .apiInfo(apiInfo())
                .select()
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket newsApiAccelerator() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("01. Accelerator Guide Program Service")
                .apiInfo(apiInfo())
                .select()
                .paths(regex("/acl.*"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("표준프레임워크 DeviceAPI 연계서비스 (Hybrid App)")
                .description("표준프레임워크 하이브리드앱 실행환경  - iOS / Android 하이브리드앱 Rest 서비스")
                .termsOfServiceUrl("https://www.egovframe.go.kr/wiki/doku.php?id=egovframework:hyb:gate_page")
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.egovframe.go.kr")
                .version("3.10")
                .build();
    }
}