package co.polarpublishing.userservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ConcurrentComputingConfig {

  @Value("${com.wp.concurrent-request.max-quantity}")
  private int poolSize;

  @Bean(name = "wpThreadPoolTaskExecutor")
  public ThreadPoolTaskExecutor buildScraperThreadPoolTaskExecutor() {
    ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
    threadPoolTaskExecutor.setCorePoolSize(poolSize);
    threadPoolTaskExecutor.setMaxPoolSize(poolSize);
    return threadPoolTaskExecutor;
  }
  
}