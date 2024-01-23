package co.polarpublishing;

import co.polarpublishing.common.configuration.SwaggerConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableDiscoveryClient
@Import(value = {SwaggerConfiguration.class})
@EnableFeignClients(basePackages = {"co.polarpublishing.userservice.feign", "co.polarpublishing.common.client"})
@ComponentScan({"co.polarpublishing.userservice", "co.polarpublishing.common"})
public class UserApplication {
  public static void main(String[] args) {
    SpringApplication.run(UserApplication.class, args);
  }
}
