package com.example.templet.conf;

import org.springframework.test.context.DynamicPropertyRegistry;

public class BucketConf {

  void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("aws.s3.bucket", () -> "dummy-bucket");
  }
}
