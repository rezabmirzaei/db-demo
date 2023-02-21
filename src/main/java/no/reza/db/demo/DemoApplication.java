package no.reza.db.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String sql = "SELECT NOW()";
		System.out.println("\n#### Checking DB connection ####");
		System.out.println("\nSELECT NOW(): " + jdbcTemplate.queryForList(sql));
		System.out.println("\n#### Success! ####\n");

	}

}
