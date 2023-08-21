package no.reza.db.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zaxxer.hikari.HikariDataSource;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@SuppressWarnings("all")
	@Override
	public void run(String... args) throws Exception {

		System.out.println("\n#### Checking DB connection ####\n");

		System.out.println("------------------------------");
		System.out.println(((HikariDataSource) jdbcTemplate.getDataSource()).getJdbcUrl());
		System.out.println("------------------------------\n");

		System.out.println("\nSELECT NOW(): " + jdbcTemplate.queryForList("SELECT NOW()"));

		System.out.println("\n#### Success! ####\n");

	}

}
