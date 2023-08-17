/* 
*  Project : Jema
*  Author  : Raj Khatri
*  Date    : 10-Mar-2023
*
*/
package com.jema.app.configs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jema.app.jwt.SecurityConstants;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

//	@Bean
//	public Docket postsApi() {
//		return new Docket(DocumentationType.SWAGGER_2).groupName("public-api").apiInfo(apiInfo()).select()
//				.paths(PathSelectors.regex("/api/v1.*")).build();
//	}

//	private Predicate<String> postPaths() {
//		return or(PathSelectors.regex("/user/login.*"),PathSelectors.regex("/user/login.*"));
//	}

//	private ApiInfo apiInfo() {
//		return new ApiInfoBuilder().title("Jema API").description("Jema Application API reference for developers")
//				.termsOfServiceUrl("https://stackgeeks.com/")
//				.contact(new Contact("Raj Khatri", "https://stackgeeks.com/", "er.rajesh1107@gmail.com"))
//				.license("Stackgeeks License").licenseUrl("er.rajesh1107@gmail.com").version("1.0").build();
//	}
	
	
	@Bean
	public Docket productApi() {
		ParameterBuilder aParameterBuilder = new ParameterBuilder();
		aParameterBuilder.name(SecurityConstants.TOKEN_HEADER) // name of header
				.modelRef(new ModelRef("string")).parameterType("header") // type - header
				.defaultValue(SecurityConstants.TOKEN_PREFIX) // based64 of - zone:mypassword
				.required(false) // for compulsory
				.build();
		List<Parameter> aParameters = new ArrayList<>();
		aParameters.add(aParameterBuilder.build()); // add parameter
		
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.jema.app.controllers"))
				// .paths(regex("/accounts.*"))
				.build().apiInfo(metaData())
				.globalOperationParameters(aParameters);
	}

	private ApiInfo metaData() {
		return new ApiInfoBuilder().title("Jema API").description("Jema Application API reference for developers")
				.termsOfServiceUrl("https://stackgeeks.com/")
				.contact(new Contact("Raj Khatri", "https://stackgeeks.com/", "er.rajesh1107@gmail.com"))
				.license("Stackgeeks License").licenseUrl("er.rajesh1107@gmail.com").version("1.0").build();
	}

}