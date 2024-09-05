package com.abhilash.project.uber.uberApp;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
public class TestContainerConfiguration {

    @Bean
    @ServiceConnection
    PostgreSQLContainer<?> postgresContainer() {
        var image = DockerImageName.parse("postgis/postgis:12-3.0")
                .asCompatibleSubstituteFor("postgres");
        return new PostgreSQLContainer<>(image);    }


}
