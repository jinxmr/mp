package com.jxm.base.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	/**
	 * 权限模块
	 */
	@Bean
	public Docket createSecurityApi() {
		return new Docket(DocumentationType.SWAGGER_2).pathMapping("/")
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.jxm.jxmsecurity.user.rest"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(new ApiInfoBuilder().title("权限模块")
						.description("权限模块API")
						.version("1.0")
						.contact(new Contact("jxm", "www.baidu.com", "jinxmsir@163.com"))
						.build());
	}
}
