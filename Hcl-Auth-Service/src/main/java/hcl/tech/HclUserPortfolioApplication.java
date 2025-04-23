package hcl.tech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class HclUserPortfolioApplication {

	public static void main(String[] args) {
		SpringApplication.run(HclUserPortfolioApplication.class, args);
	}

}
