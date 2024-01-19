package co.polarpublishing.common.configuration;

import com.google.common.base.Predicates;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

	@SuppressWarnings("null")
	@Bean
	public Docket mailServiceApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(mailServiceApiInfo())
				.useDefaultResponseMessages(false)

				.globalResponseMessage(RequestMethod.GET, getResponseMessages())
				.globalResponseMessage(RequestMethod.POST, getResponseMessages())
				.globalResponseMessage(RequestMethod.PUT, getResponseMessages())
				.globalResponseMessage(RequestMethod.DELETE, getResponseMessages())
				.globalResponseMessage(RequestMethod.PATCH, getResponseMessages())

				.forCodeGeneration(false)
				.select()
				.apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
				.paths(PathSelectors.ant("/api/**"))
				.build();
	}

	private ApiInfo mailServiceApiInfo() {
		return new ApiInfoBuilder()
				.title("Documentation for Service REST APIs")
				.version("1.0")
				.build();
	}

	private List<ResponseMessage> getResponseMessages() {
		List<ResponseMessage> responseMessages = new ArrayList<>();

		responseMessages.add(
				new ResponseMessageBuilder()
						.code(500)
						.message("Failure. Unexpected condition was encountered.")
						.build());

		responseMessages.add(
				new ResponseMessageBuilder()
						.code(400)
						.message("Failure. Indicates the request contains invalid data.")
						.build());

		responseMessages.add(
				new ResponseMessageBuilder()
						.code(401)
						.message("Failure. Indicates the request contains invalid Authorization.")
						.build());

		responseMessages.add(
				new ResponseMessageBuilder()
						.code(404)
						.message("Failure. Indicates the requested resource was not found.")
						.build());

		return responseMessages;
	}

}
