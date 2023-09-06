package com.education.trainingmicroservice;

import com.education.trainingmicroservice.service.HealthCheckService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@RequiredArgsConstructor
class HealthCheckControllerTests {

	@LocalServerPort
	private int port;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private HealthCheckService healthCheckService;

	@Test
	@SneakyThrows
	void contextLoads() {
		String expectedValue = objectMapper.writeValueAsString(healthCheckService.getStatusOk());
		String url = "http://localhost:" + port + "/health";
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		Assertions.assertEquals(expectedValue, response.getBody());
	}

}
