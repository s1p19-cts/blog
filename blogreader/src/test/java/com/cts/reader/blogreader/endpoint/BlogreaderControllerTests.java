package com.cts.reader.blogreader.endpoint;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.assertj.core.api.BDDAssertions.then;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BlogreaderControllerTests {

	private static final Logger log = LoggerFactory.getLogger(BlogreaderControllerTests.class);

	@LocalServerPort
	private int port;

	@Value("${local.management.port}")
	private int mgt;

	private TestRestTemplate testRestTemplate = new TestRestTemplate();

	@Rule
	public TestName name = new TestName();

	@Test
	public void contextLoads() {
		log.info("..... (-) {}", name.getMethodName());
		log.info("... local port is : {}", port);
	}

	@Test
	public void shouldReturn200WhenSendingRequestToRootEndpoint() throws Exception {
		log.info("... local port is : {}", port);
		@SuppressWarnings("rawtypes")
		ResponseEntity<String> entity = this.testRestTemplate.getForEntity(
				"http://localhost:" + this.port + "/", String.class);
		then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void shouldReturn200WhenSendingRequestToBlogEndpoint() throws Exception {
		log.info("... local port is : {}", port);
		@SuppressWarnings("rawtypes")
		ResponseEntity<Iterable> entity = this.testRestTemplate.getForEntity(
				"http://localhost:" + this.port + "/blogs", Iterable.class);
		then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		log.info("....... got blogs - " + entity.getBody().toString());
		log.info(".......   body is - " + entity.getBody().getClass());
		entity.getBody().forEach(blog -> {
			log.info("... " + blog.toString());
		});
	}

	@Test
	public void shouldReturn200WhenSendingRequestToManagementEndpoint() throws Exception {
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
				"http://localhost:" + this.mgt + "/actuator/info", Map.class);

		then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void shouldReturn200WhenSendingRequestToBlogSearchEndpoint() throws Exception {
		log.info("... local port is : {}", port);
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
				"http://localhost:" + this.port + "/blog/1", Map.class);
		then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		log.info("....... got blogs - " + entity.getBody().toString());
		log.info(".......   body is - " + entity.getBody().getClass());
		entity.getBody().forEach((k, v) -> log.info("... " + k + ":" + v));
	}

}
