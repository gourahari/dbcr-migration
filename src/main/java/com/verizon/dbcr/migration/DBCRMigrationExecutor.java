package com.verizon.dbcr.migration;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.MigrationInfoService;
import org.flywaydb.core.internal.info.MigrationInfoDumper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Configuration
public class DBCRMigrationExecutor {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${command}")
    private String command;

    @Value("${spring.flyway.showLastInfo}")
    private int showLastInfo;

    @Bean
    public FlywayMigrationStrategy cleanMigrateStrategy() {
        return new FlywayMigrationStrategy() {
            @Override
            public void migrate(Flyway flyway) {
                if ("migrate".equalsIgnoreCase(command)) {
                    try {
                        flyway.repair();
                        flyway.migrate();
                    } catch (Exception ex) {
                        ex.printStackTrace(System.err);
                    }
                }

                // Showing info
                MigrationInfoService info = flyway.info();
                MigrationInfo current = info.current();
//                MigrationVersion currentSchemaVersion = current == null ? MigrationVersion.EMPTY : current.getVersion();
//                MigrationVersion schemaVersionToOutput = currentSchemaVersion == null ? MigrationVersion.EMPTY : currentSchemaVersion;
//                System.out.println("Latest schema version: " + schemaVersionToOutput);
                System.out.println(MigrationInfoDumper.dumpToAsciiTable(
                        -1 == showLastInfo? info.all() : getLastNItems(info.all(), showLastInfo))
                );
           }
        };
    }

    private static MigrationInfo[] getLastNItems(MigrationInfo[] records, final int n) {
        System.out.println(String.format("Showing last %d records...", n));
        MigrationInfo[] result = new MigrationInfo[Math.min(records.length, n)];
        return Arrays.asList(records).stream()
                .skip(Math.max(0, records.length - n))
                .collect(Collectors.toList())
                .toArray(result);
    }
}