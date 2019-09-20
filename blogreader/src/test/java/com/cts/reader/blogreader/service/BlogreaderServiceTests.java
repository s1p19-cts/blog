package com.cts.reader.blogreader.service;

import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BlogreaderServiceTests {
	private static final Logger log = LoggerFactory.getLogger(BlogreaderServiceTests.class);

	@Autowired
	BlogreaderService blogReader;

	@Rule
	public TestName name = new TestName();

	@Test
	public void contextLoads() {
		log.info("..... (-) {}", name.getMethodName());
		Assertions.assertNotNull(blogReader, "service instance auto wire failed ");
		log.info("... application service context load");
	}

	@Test
	public void testGetInfo() {
		log.info("..... (-) {}", name.getMethodName());
		Assertions.assertNotNull(blogReader, "service instance auto wire failed ");
		log.info (blogReader.getInfo());
	}

	@Test
	public void testGreeting() {
		log.info("..... (-) {}", name.getMethodName());
		Assertions.assertNotNull(blogReader, "service instance auto wire failed ");
		String greeting = blogReader.getGreeting("homer");
		Assertions.assertNotNull(greeting);
		Assertions.assertTrue(greeting.contains("Hello homer"));
		log.info("... " + greeting);
	}

	@Test
	public void testFindAll() {
		log.info("..... (-) {}", name.getMethodName());
		Iterable blogs = blogReader.getBlogs();
		Assertions.assertNotNull(blogs, "failed to get all blogs ");
		log.info("....... got all blogs - ", blogs.toString());
		blogs.forEach(blog ->
			log.info("... " + blog.toString()
			// blog.forEach((k, v) -> log.info((k + ":" + v))
		));
	}

	@Test
	public void testFindById() {
		log.info("..... (-) {}", name.getMethodName());
		String id = "1";
		Map blog = blogReader.getBlogById(id);
		Assertions.assertNotNull(blog, "failed to get blog for id - " + id);
		log.info("... blog is - {}", blog);
	}

}
