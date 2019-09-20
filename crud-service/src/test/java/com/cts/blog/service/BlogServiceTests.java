package com.cts.blog.service;

import com.cts.blog.model.Blog;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:test-schema-h2.sql")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:test-data-h2.sql")
public class BlogServiceTests {
	private static final Logger log = LoggerFactory.getLogger(BlogService.class);

	@Autowired
	BlogService blogService;

	@Rule
	public TestName name = new TestName();

	@Test
	public void contextLoads() {
		log.info("..... (-) {}", name.getMethodName());
		Assertions.assertNotNull(blogService, "service instance auto wire failed ");
		log.info("... application service context load");
	}

	@Test
	public void testGetInfo() {
		log.info("..... (-) {}", name.getMethodName());
		Assertions.assertNotNull(blogService, "service instance auto wire failed ");
		String greeting = blogService.getInfo();
		Assertions.assertNotNull(greeting);
		Assertions.assertTrue(greeting.contains("'blog-crud-service' is running"));
		log.info("... " + greeting);
	}

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


	@Test
	public void testCreate() {
		log.info("..... (-) {}", name.getMethodName());
		Blog blog = blogService.create("TESTADD", "testing add");
		Assertions.assertNotNull(blog, "Create failed! Blog is empty ");
		Assertions.assertTrue(blog.getId() != null && blog.getId().longValue() > 0, "Create failed! Blog id is <= 0");
		log.info("... created blog - {}", blog);
	}

	@Test
	public void testUpdate() {
		log.info("..... (-) {}", name.getMethodName());

		Blog blog1 = blogService.create("TESTUPDATE", "UPDATE ME");
		Assertions.assertNotNull(blog1, "Create failed! Blog is empty ");
		Assertions.assertTrue(blog1.getId() != null && blog1.getId().longValue() > 0, "Create failed! Blog id is <= 0");
		log.info("... created blog - {}", blog1);

		Blog blog2 = blogService.update(blog1.getId().toString(), blog1.getTitle(),
				"Laborum maxime doloremque sunt esse tenetur nisi soluta. Amet voluptatem quos nemo mollitia illum. Est animi rerum animi enim rerum culpa. Perspiciatis perspiciatis magnam soluta totam voluptatem mollitia sit. Qui corporis quos et. Quia velit et optio. Vero commodi asperiores voluptas sit. Modi vitae molestiae nisi ducimus et facilis. Nisi quia assumenda minima et in. Est laudantium voluptate ratione. Praesentium natus animi saepe doloremque impedit eaque sit autem. Voluptates voluptatum quibusdam distinctio recusandae ex nihil velit. Iure consequatur aut nemo alias libero non. Id officia dolore et laboriosam nemo. Sed voluptas quo rerum est culpa.");
		Assertions.assertNotNull(blog2, "Failed to update blog for id -  " + blog1.getId());
		Assertions.assertTrue(blog1.getId() == blog2.getId(), "Error: update changed the id");
		Assertions.assertTrue(blog1.getContent().equals(blog2.getContent()) == false, "Error: content not updated correctly.");
		log.info("... updated blog is - {}", blog2);
	}

	@Test
	public void testDelete() {
		log.info("..... (-) {}", name.getMethodName());

		Blog blog1 = blogService.create("TESTDELETE", "DELETE ME");
		Assertions.assertNotNull(blog1, "Create failed! Blog is empty ");
		Assertions.assertTrue(blog1.getId() != null && blog1.getId().longValue() > 0, "Create failed! Blog id is <= 0");
		log.info("... created blog - {}", blog1);

		blogService.delete(blog1.getId().toString());

		Blog blog = blogService.findById(blog1.getId().toString());
		Assertions.assertNull(blog, "Error: delete failed! Found blog for id -  " + blog1.getId());
		log.info("... delete succeeded!");
	}

}
