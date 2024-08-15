package com.example.templet.conf;

import org.springframework.test.context.DynamicPropertyRegistry;

public class EmailConf {

  void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("aws.ses.source", () -> "dummy-ses-source");
  }
}
