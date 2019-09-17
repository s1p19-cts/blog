package com.cts.blog;

import com.cts.blog.service.BlogService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	private static final Logger log = LoggerFactory.getLogger(BlogService.class);

	@Test
	public void contextLoads() {
		log.info(" application context load");
	}

}
