package com.rudderstack.tracking.webserver;

import com.rudderstack.trackingplan.controller.TrackingPlanRoutingController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class TrackingPlanWebserverApplication {

	public static void main(String[] args) {

		SpringApplication.run(TrackingPlanWebserverApplication.class, args);

		System.out.println("Tracking Plan Application started....");
	}


//	@Configuration
//	public class SpringFoxConfig {
//		@Bean
//		public Docket api() {
//			return new Docket(DocumentationType.SWAGGER_2)
//				.select()
//				.apis(RequestHandlerSelectors.any())
//				.paths(PathSelectors.any())
//				.build();
//		}
//	}

	@Bean
	public TrackingPlanRoutingController getTrackingPlanRoutingController() {
		return new TrackingPlanRoutingController();
	}

}
