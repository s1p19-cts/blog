package com.cts.blog.endpoint;

import com.cts.blog.model.Blog;
import com.cts.blog.service.BlogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class BlogController {

	private static final Logger log = LoggerFactory.getLogger(BlogController.class);

	@Autowired
	private BlogService blogService;

	@RequestMapping("/")
	public ResponseEntity<String> info() {
		return new ResponseEntity<String>(blogService.getInfo(), HttpStatus.OK);
	}

	@RequestMapping("/greeting")
	public String greeting() {
		return "Hello earthling!\n";
	}

	@RequestMapping("/greeting/{name}")
	public String greeting(@PathVariable("name") String name) {
		return String.format("Hello %s!\n", name);
	}

	@RequestMapping(value = "/blogs", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Iterable<Blog>> index() {
		return new ResponseEntity<Iterable<Blog>>(blogService.findAll(), HttpStatus.OK);
	}

	@GetMapping("/blog/{id}")
	@ResponseBody
	public ResponseEntity<Blog> show(@PathVariable String id) {
		log.debug(" ... searching for {}", id);
		Blog blog = blogService.findById(id);
		if (blog == null) {
			return new ResponseEntity<Blog>(blog, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Blog>(blog, HttpStatus.OK);
		}
	}

	@PostMapping("/blog/search")
	@ResponseBody
	public List<Blog> search(@RequestBody Map<String, String> body) {
		String searchTerm = body.get("text");
		return blogService.search(searchTerm);
	}

	@PostMapping("/blog")
	@ResponseBody
	public Blog create(@RequestBody Map<String, String> body) {
		String title = body.get("title");
		String content = body.get("content");
		return blogService.create(title, content);
	}

	@PutMapping("/blog/{id}")
	@ResponseBody
	public Blog update(@PathVariable String id, @RequestBody Map<String, String> body) {
		return blogService.update(id, body.get("title"), body.get("content"));
	}

	@DeleteMapping("blog/{id}")
	@ResponseBody
	public boolean delete(@PathVariable String id) {
		blogService.delete(id);
		return true;
	}
}