package edu.miu.carfleet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class CarfleetApplication implements CommandLineRunner {
	@Autowired
	private MongoTemplate mongoTemplate;
	public static void main(String[] args) {
		SpringApplication.run(CarfleetApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		mongoTemplate.getDb().drop();
	}
}
