package com.example.templet.endpoint.rest.controller;

import static com.example.templet.endpoint.rest.controller.health.PingController.OK;

import com.example.templet.conf.FacadeIT;
import com.example.templet.endpoint.rest.controller.health.HealthDbController;
import com.example.templet.endpoint.rest.controller.health.PingController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class HealthControllerIT extends FacadeIT {

  @Autowired PingController pingController;
  @Autowired HealthDbController healthDbController;

  @Test
  void ping() {
    Assertions.assertEquals("pong", pingController.ping());
  }

  @Test
  void can_read_from_dummy_table() {
    var responseEntity = healthDbController.dummyTable_should_not_be_empty();
    Assertions.assertEquals(OK, responseEntity);
  }
}
