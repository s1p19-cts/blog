package com.cts.blog.endpoint;

import com.cts.blog.service.BlogService;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.assertj.core.api.BDDAssertions.then;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:schema-h2.sql")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:data-h2.sql")
public class BlogControllerTests {
	private static final Logger log = LoggerFactory.getLogger(BlogService.class);

	@LocalServerPort
	private int port;

	@Value("${local.management.port}")
	private int mgt;

	private TestRestTemplate testRestTemplate = new TestRestTemplate();

	@Rule public TestName name = new TestName();

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


/*

	@Test
	public void testFindAll() {
		log.info("..... (-) {}", name.getMethodName());
		Iterable<Blog> blogs = blogService.findAll();
		Assertions.assertNotNull(blogs, "failed find all blogs ");
		log.info("....... got all blogs - ");
		blogs.forEach(blog -> {
			log.info("... " + blog.toString());
		});
	}

	@Test
	public void testFindById() {
		log.info("..... (-) {}", name.getMethodName());
		long id = 1;
		Blog blog = blogService.findById(id);
		Assertions.assertNotNull(blog, "failed find blog for id -  " + id);
		log.info("... blog is - {}", blog);
	}

	@Test
	public void testSearch() {
		log.info("..... (-) {}", name.getMethodName());
		String searchTerm = "lorem";
		List<Blog> blogs = blogService.search(searchTerm);
		Assertions.assertNotNull(blogs, "failed to search blog for - " + searchTerm);
		log.info("....... got all blogs - ");
		blogs.forEach(blog -> {
			log.info("... " + blog.toString());
		});
	}

	@Test
	public void testSearchNotFound() {
		log.info("..... (-) {}", name.getMethodName());
		String searchTerm = "notfound";
		List<Blog> blogs = blogService.search(searchTerm);
		Assertions.assertTrue(blogs == null || blogs.isEmpty(), "found a blog when expecting null for - " + searchTerm);
		log.info("... no entries found for - {}", searchTerm);
	}

 */
}
