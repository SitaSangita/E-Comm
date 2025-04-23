package in.hcl.app.router;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyRouter {

	@Bean
	RouteLocator routeLocator(RouteLocatorBuilder builder)
	{
		return builder.routes().route("MS1",r->r.path("/ms1/**").uri("http://localhost:8081"))
							   //.route("MS2",r->r.path("/ms2/**").uri("lb://MS2"))
							   //.route("MS3",r->r.path("/ms3/**").uri("lb://MS3"))
							   //.route("MS4",r->r.path("/ms4/**").uri("http://localhost:8089"))
							   .build();
	}
}