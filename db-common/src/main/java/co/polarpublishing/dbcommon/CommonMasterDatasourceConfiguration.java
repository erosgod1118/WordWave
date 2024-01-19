package co.polarpublishing.dbcommon;

import com.zaxxer.hikari.HikariDataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class CommonMasterDatasourceConfiguration {

	@Bean
	@Primary
	@ConfigurationProperties("spring.datasource")
	public DataSourceProperties masterDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean(name = "masterDataSource")
	@Primary
	@ConfigurationProperties("spring.datasource")
	public DataSource masterDataSource() {
		return masterDataSourceProperties()
						.initializeDataSourceBuilder()
						.type(HikariDataSource.class).build();
	}

}
