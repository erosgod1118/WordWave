package co.polarpublishing.dbcommon;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class CommonReadReplicaDatasourceConfiguration {

	@Bean
	@ConfigurationProperties("app.read.datasource")
	public DataSourceProperties readReplicaDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean(name = "readReplicaDataSource")
	@ConfigurationProperties("app.read.datasource")
	public DataSource readRepliceDataSource() {
		return readReplicaDataSourceProperties().initializeDataSourceBuilder()
						.type(HikariDataSource.class).build();
	}

}
