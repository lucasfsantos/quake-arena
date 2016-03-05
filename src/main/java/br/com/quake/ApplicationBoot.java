package br.com.quake;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import io.swagger.annotations.Api;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
@PropertySource({"classpath:application.properties"})
@EnableSwagger2
public class ApplicationBoot extends SpringBootServletInitializer {
	/**
	 * Main para inicialização do SpringBootServletInitializer.
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(ApplicationBoot.class);
	}
	
	/**
	 * @param applicationName 
	 * @return API description
	 */
	@Bean
	public Docket swagger(@Value("${spring.application.name}") String applicationName) {
		return new Docket(DocumentationType.SWAGGER_2)
			.apiInfo(apiInfo())
			.pathMapping("/" + applicationName)
			.select()
				.apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
			.build();
	}
	
	private static ApiInfo apiInfo() {
		return new ApiInfoBuilder()
			.title("Quake Arena")
			.description("Serviços responsáveis pela consulta que retorne relatório de cada jogo do Quake Arena.")
			.version("1.0")
		.build();
	}
}
