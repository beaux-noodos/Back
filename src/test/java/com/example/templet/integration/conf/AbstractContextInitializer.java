package com.example.templet.integration.conf;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;

public abstract class AbstractContextInitializer
    implements ApplicationContextInitializer<ConfigurableApplicationContext> {
  private static final String SENTRY_MOCK_DSN = "https://examplePublicKey@example.sentry.io/12345";

  @Override
  public void initialize(ConfigurableApplicationContext applicationContext) {
    PostgreSQLContainer<?> postgresContainer =
        new PostgreSQLContainer<>("postgres:13.9")
            .withDatabaseName("it-db")
            .withUsername("sa")
            .withPassword("sa");
    postgresContainer.start();

    String flywayTestdataPath = "classpath:/db/testdata";
    TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
        applicationContext,
        "sentry.dsn=" + SENTRY_MOCK_DSN,
        "env=test",
        "server.port=" + this.getServerPort(),
        "aws.eventBridge.bus=dummy",
        "aws.sqs.queue.url=dummy",
        "aws.ses.source=dummy",
        "aws.ses.contact=dummy",
        "aws.region=dummy",
        "firebase.private.key=dummy",
        "aws.s3.bucket=dummy",
        "spring.datasource.url=" + postgresContainer.getJdbcUrl(),
        "spring.datasource.username=" + postgresContainer.getUsername(),
        "spring.datasource.password=" + postgresContainer.getPassword(),
        "spring.flyway.locations=classpath:/db/migration," + flywayTestdataPath);
  }

  public abstract int getServerPort();
}
