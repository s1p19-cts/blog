package com.cts.blog.service;

import com.cts.blog.model.Blog;
import com.cts.blog.model.Config;
import com.cts.blog.repository.BlogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Configuration
@Service
public class BlogService {

	private static final Logger log = LoggerFactory.getLogger(BlogService.class);

	@Autowired
	private BlogRepository blogRepository;

	@Autowired
	private Config config;

	public String getInfo() {
		StringBuffer buf = new StringBuffer();
		buf.append("<h3>");
		buf.append(" blog service - '").append(config.getName()).append("' is running ");
		buf.append(" region - " + config.getRegion());
		buf.append("</h3>");
		return buf.toString();
	}

	public Iterable<Blog> findAll() {
		Iterable<Blog> blogs = blogRepository.findAll();
		log.info(" findAll returned - {} rows", blogRepository.count());
		return blogs;
	}

	public Blog findById(String id) {
		return this.findById(Long.parseLong(id));
	}

	public Blog findById(long id) {
		log.debug(" ... searching for {}", id);
		Optional<Blog> blog = blogRepository.findById(id);
		return blog.isPresent() ? blog.get() : null;
	}

	public List<Blog> search(String searchTerm) {
		log.debug(" ... searching for '{}'", searchTerm);
		return blogRepository.findByTitleContainingOrContentContaining(searchTerm, searchTerm);
	}

	public Blog create(String title, String content) {
		return blogRepository.save(new Blog(title, content));
	}

	public Blog update(String id, String title, String content) {
		Blog blog = findById(id);
		if (blog != null) {
			blog.setTitle(title);
			blog.setContent(content);
			blog = blogRepository.save(blog);
		}
		return blog;
	}

	public void delete(String id) {
		blogRepository.delete(findById(id));
	}

}
