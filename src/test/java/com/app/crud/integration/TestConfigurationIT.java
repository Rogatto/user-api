package com.app.crud.integration;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.RabbitMQContainer;

import java.util.Arrays;

@AutoConfigureMockMvc
@ActiveProfiles("integration")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestConfigurationIT {


    static final String IMAGE_VERSION_MSQL = "mysql:5.7.31";
    static final String IMAGE_VERSION_RABBIT = "rabbitmq:3.8-management";
    static final String IMAGE_VERSION_REDIS = "redis:5.0.3-alpine";
    static final MySQLContainer mySQLContainer;
    static final RabbitMQContainer rabbitMQContainer;
    static final GenericContainer redisContainer;

    static {
        mySQLContainer = new MySQLContainer(IMAGE_VERSION_MSQL)
                .withDatabaseName("crudbase")
                .withUsername("root")
                .withPassword("root");

        mySQLContainer.setPortBindings(Arrays.asList("3306:3306"));
        mySQLContainer.start();

        rabbitMQContainer = new RabbitMQContainer(IMAGE_VERSION_RABBIT);
        rabbitMQContainer.withFileSystemBind("docker/rabbit_enabled_plugins", "/etc/rabbitmq/enabled_plugins", BindMode.READ_WRITE);
        rabbitMQContainer.setPortBindings(Arrays.asList("61613:61613"));
        rabbitMQContainer.start();

        redisContainer = new GenericContainer<>(IMAGE_VERSION_REDIS);
        redisContainer.setPortBindings(Arrays.asList("6379:6379"));
        redisContainer.start();
    }
}
