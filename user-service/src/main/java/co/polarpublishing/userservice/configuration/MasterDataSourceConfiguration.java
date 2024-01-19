package co.polarpublishing.userservice.configuration;

import co.polarpublishing.dbcommon.CommonDatasourceConfiguration;
import co.polarpublishing.dbcommon.CommonMasterDatasourceConfiguration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
	entityManagerFactoryRef = "masterEntityManagerFactory",
	transactionManagerRef = "masterTransactionManager",
	basePackages = "co.polarpublishing.userservice.repository.write"
)
@Import(value = {CommonMasterDatasourceConfiguration.class, CommonDatasourceConfiguration.class})
public class MasterDataSourceConfiguration {

	@Primary
	@Bean(name = "masterEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean masterEntityManagerFactory(EntityManagerFactoryBuilder builder,
					@Qualifier("masterDataSource") DataSource dataSource,
					@Qualifier("commonJpaProperties") Map<String, String> jpaProperties) {
		return builder
					.dataSource(dataSource)
					.packages("co.polarpublishing.dbcommon.entity", "co.polarpublishing.userservice.entity")
					.persistenceUnit("master-db")
					.properties(jpaProperties)
					.build();
	}

	@Primary
	@Bean(name = "masterTransactionManager")
	public PlatformTransactionManager masterTransactionManager(@Qualifier("masterEntityManagerFactory") EntityManagerFactory masterEntityManagerFactory) {
		return new JpaTransactionManager(masterEntityManagerFactory);
	}

}
