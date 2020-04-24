package com.jxm.jxmsecurity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@MapperScan("com.jxm.jxmsecurity.user.dao")
public class JxmSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(JxmSecurityApplication.class, args);
	}

	/**
	 * 权限模块
	 */
	@Bean
	public Docket createSecurityApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("Security")
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
