package com.verizon.dbcr.migration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;

@SpringBootApplication
public class DBCRMigrationApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(DBCRMigrationApplication.class, args);
	}

}
