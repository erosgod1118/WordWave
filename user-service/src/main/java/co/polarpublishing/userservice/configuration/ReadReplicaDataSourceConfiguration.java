package co.polarpublishing.userservice.configuration;

import co.polarpublishing.dbcommon.CommonDatasourceConfiguration;
import co.polarpublishing.dbcommon.CommonReadReplicaDatasourceConfiguration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
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
        entityManagerFactoryRef = "readReplicaEntityManagerFactory",
        transactionManagerRef = "readReplicaTransactionManager",
        basePackages = "co.polarpublishing.userservice.repository.read")
@Import(value = {CommonReadReplicaDatasourceConfiguration.class, CommonDatasourceConfiguration.class})
public class ReadReplicaDataSourceConfiguration {

	@Bean(name = "readReplicaEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean readReplicaEntityManagerFactory(
					EntityManagerFactoryBuilder builder,
					@Qualifier("readReplicaDataSource") DataSource dataSource,
					@Qualifier("commonJpaProperties") Map<String, String> jpaProperties) {
		return builder
					.dataSource(dataSource)
					.packages("co.polarpublishing.dbcommon.entity", "co.polarpublishing.userservice.entity")
					.persistenceUnit("replica-db")
					.properties(jpaProperties)
					.build();
	}

	@Bean(name = "readReplicaTransactionManager")
	public PlatformTransactionManager readReplicaTransactionManager(@Qualifier("readReplicaEntityManagerFactory") EntityManagerFactory readReplicaEntityManagerFactory) {
		return new JpaTransactionManager(readReplicaEntityManagerFactory);
	}

}
