package co.polarpublishing.dbcommon;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CommonDatasourceConfiguration {

	@Value("${hibernate.physical_naming_strategy}")
	private String hibernatePhycialNamingStrategy;

	@Value("${hibernate.implicit_naming_strategy}")
	private String hibernateImplicitNamingStrategy;

	@Bean(name = "commonJpaProperties")
	public Map<String, String> commonJpaProperties() {
		Map<String, String> props = new HashMap<>();

		props.put("hibernate.physical_naming_strategy", hibernatePhycialNamingStrategy);
		props.put("hibernate.implicit_naming_strategy", hibernateImplicitNamingStrategy);
		
		return props;
	}

}
