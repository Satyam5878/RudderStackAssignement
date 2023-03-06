package com.rudderstack.tracking.webserver;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.rudderstack.trackingplan.controller.EventRoutingController;
import com.rudderstack.trackingplan.controller.TrackingPlanRoutingController;
import com.rudderstack.trackingplan.domain.repository.EventRepository;
import com.rudderstack.trackingplan.domain.repository.TrackingPlanRepository;
import com.rudderstack.trackingplan.domain.repository.impl.EventRepositoryMongoImpl;
import com.rudderstack.trackingplan.domain.repository.impl.TrackingPlanRepositoryMongoImpl;
import com.rudderstack.trackingplan.domain.repository.model.EventDbDocument;
import com.rudderstack.trackingplan.domain.repository.model.TrackingPlanDbDocument;
import com.rudderstack.trackingplan.domain.repository.model.TrackingPlanRuleDocument;
import com.rudderstack.trackingplan.domain.service.EventService;
import com.rudderstack.trackingplan.domain.service.TrackingPlanService;
import com.rudderstack.trackingplan.domain.service.impl.EventServiceImpl;
import com.rudderstack.trackingplan.domain.service.impl.TrackingPlanServiceImpl;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;
import java.time.Clock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@SpringBootApplication
public class TrackingPlanWebserverApplication {

	private final String TRACKING_PLAN_DB = "trackingPlanDb";

	public static void main(String[] args) {

		SpringApplication.run(TrackingPlanWebserverApplication.class, args);

		System.out.println("Tracking Plan Application started....");
	}

	@Bean
	public TrackingPlanRoutingController getTrackingPlanRoutingController(
		@Autowired TrackingPlanService trackingPlanService
	) { return new TrackingPlanRoutingController(trackingPlanService); }

	@Bean
	public EventRoutingController getEventRoutingController(
		@Autowired EventService eventService
	) { return new EventRoutingController(eventService); }

	@Bean
	public TrackingPlanService getTrackingPlanServiceImpl(
		@Autowired TrackingPlanRepository trackingPlanRepository
		) {
		return new TrackingPlanServiceImpl(
			trackingPlanRepository
		);
	}

	@Bean
	public EventService getEventService(@Autowired EventRepository eventRepository) {
		return new EventServiceImpl(eventRepository);
	}

	@Bean
	public TrackingPlanRepository getTrackingPlanRepository(@Autowired MongoOperations mongoOperations) {
		return new TrackingPlanRepositoryMongoImpl(mongoOperations, getClock());
	}

	@Bean
	public EventRepository getEventRepository(@Autowired MongoOperations mongoOperations) {
		return new EventRepositoryMongoImpl(mongoOperations, getClock());
	}

//	@Bean
//	public MongoOperations getMongoOperations() {
//		MongoOperations mongoOperations = getMongoOperationsForDB("trackingPlan");
//
//		loadStaticData(mongoOperations);
//		return mongoOperations;
//	}

	@Bean
	public MongodConfig getMongodConfig() throws IOException {
		return MongodConfig.builder()
			.version(Version.Main.valueOf("V5_0"))
			.net(new Net())
			.build();
	}

	@Bean
	public MongodStarter getMongodStarter() {
		return MongodStarter.getDefaultInstance();
	}

	@Bean
	public MongodExecutable getMongoExecutable(
		@Autowired MongodStarter mongodStarter,
		@Autowired MongodConfig mongodConfig
	) {
		return mongodStarter.prepare(mongodConfig);
	}


	@Bean
	public MongoOperations getMongoOperations(
		@Autowired MongodExecutable mongodExecutable,
		@Autowired MongodConfig mongodConfig
		//String dbName
	) {
		// Booting embedded mongo server.
		try {
			//MongodStarter mongodStarter = MongodStarter.getDefaultInstance();
//			MongodConfig mongodConfig = MongodConfig.builder()
//				.version(Version.Main.valueOf("V3_0"))
//				.net(new Net())
//				.build();
			//MongodExecutable mongodExecutable = mongodStarter.prepare(mongodConfig);
			mongodExecutable.start();
			MongoClient mongoClient = MongoClients.create(
				String.format(
					"mongodb://%s:%s/%s",
					mongodConfig.net().getServerAddress().getHostAddress(),
					mongodConfig.net().getPort(),
					TRACKING_PLAN_DB
				)
			);
			MongoOperations mongoOperations = new MongoTemplate(mongoClient, TRACKING_PLAN_DB);
				loadStaticData(mongoOperations);
			return mongoOperations;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void loadStaticData(MongoOperations mongoOperations) {
		String event1 = "Event 1 Created";
		String event2 = "Event 2 Created";
		List<EventDbDocument> eventDbDocuments = new ArrayList<>();
		eventDbDocuments.add(
			new EventDbDocument(
				EventDbDocument.getEventNameKey(event1),
				event1,
				"Creating very first event",
				new HashMap<>()
			));
		eventDbDocuments.add(
			new EventDbDocument(
				EventDbDocument.getEventNameKey(event1),
				event2,
				"Creating very second event",
				new HashMap<>()
			));
//		mongoOperations.insert(
//			new TrackingPlanDbDocument(
//				"First Tracking Plan"//,
////				new TrackingPlanRuleDocument(
////					//eventDbDocuments
////				) // add some actual details
//			)
//		);
	}

	private Clock getClock() {
		return Clock.systemUTC();
	}

}
