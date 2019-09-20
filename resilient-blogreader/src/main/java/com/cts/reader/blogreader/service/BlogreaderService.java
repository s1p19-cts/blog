package com.cts.reader.blogreader.service;

import com.cts.reader.blogreader.model.Config;
import com.cts.reader.blogreader.utils.IterableUtils;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class BlogreaderService {
	private static final Logger log = LoggerFactory.getLogger(BlogreaderService.class);

	@Autowired
	private Config configs;

	@Autowired
	Environment environment;

	public String getInfo() {
		StringBuffer buf = new StringBuffer();
		buf.append("<h3>");
		buf.append(" blog reader - '").append(configs.getName()).append("' is running ");
		buf.append(" on [port: ").append(environment.getProperty("local.server.port")).append("] ");
		buf.append(" [region: " + configs.getRegion()).append("]");
		buf.append("</h3>");
		buf.append(" <h4> blog service endpoints:- </h4> ").append(configs.getPrimaryEndpoint());
		buf.append(" <br/> ").append(configs.getSecondaryEndpoint());
		return buf.toString();
	}

	@HystrixCommand(fallbackMethod = "defaultGreeting")
	public String getGreeting(String username) {
		return new RestTemplate()
				       .getForObject("http://127.0.0.1:4444/greeting/{username}",
						       String.class, username);
	}

	@HystrixCommand(fallbackMethod = "getBlogsFromSecondary")
	public Iterable getBlogs() {
		return getBlogsFromEndpoint(configs.getPrimaryEndpoint());
	}

	@HystrixCommand(fallbackMethod = "defaultNotAvailable")
	public Iterable getBlogsFromSecondary() {
		return getBlogsFromEndpoint(configs.getSecondaryEndpoint());
	}

	@HystrixCommand(fallbackMethod = "getBlogSecondaryById")
	public Iterable<Map> getBlogById(String id) {
		return getBlogFromEndpointById(configs.getPrimaryEndpoint(), id);
	}

	@HystrixCommand(fallbackMethod = "defaultNotAvailable")
	public Iterable<Map> getBlogSecondaryById(String id) {
		return getBlogFromEndpointById(configs.getSecondaryEndpoint(), id);
	}

	/**
	 * Get blogs from the given endpoitn
	 *
	 * @param endpoint
	 * @return
	 */
	private Iterable getBlogsFromEndpoint(String endpoint) {
		Iterable blogs = null;

		if (StringUtils.isBlank(endpoint) == false) {
			RestTemplate restTemplate = new RestTemplate();
			log.info(" ... initiated restTemplate - {}", restTemplate);
			ResponseEntity<Iterable<Map>> response = restTemplate.exchange(
					endpoint + "/blogs",
					HttpMethod.GET,
					null,
					new ParameterizedTypeReference<Iterable<Map>>() {
					});
			blogs = response.getBody();
		}
		return blogs;
	}

	/**
	 * Get blog for given blog id, from the endpoint.
	 *
	 * @param endpoint
	 * @param id
	 * @return
	 */
	private Iterable getBlogFromEndpointById(String endpoint, String id) {

		Iterable blog = null;

		if (StringUtils.isBlank(endpoint) == false) {
			RestTemplate restTemplate = new RestTemplate();
			log.info(" ... initiated restTemplate - {}", restTemplate);

			//		HttpHeaders headers = new HttpHeaders();
			//		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
			//		HttpEntity<String> entity = new HttpEntity<>(headers);

			Map<String, String> params = new HashMap<String, String>();
			params.put("id", id);

			ResponseEntity<Iterable> response = restTemplate.exchange(
					endpoint + "/blog/{id}",
					HttpMethod.GET,
					null,
					new ParameterizedTypeReference<Iterable>() {
					}, params);
			blog = response.getBody();
		}
		return blog;
	}

	// return a default message
	private Iterable defaultNotAvailable() {
		return IterableUtils.getIterableFromIterator(defaultNotFound().entrySet().iterator());
	}

	// return a default message
	private Map<String, String> defaultNotFound() {
		return defaultNotFound("No server available to get blog(s)!");
	}

	// return a default message
	private Map<String, String> defaultNotFound(String msg) {
		Map<String, String> amap = new HashMap<String, String>();
		amap.put("500", msg);
		return amap;
	}

	private String defaultGreeting(String username) {
		return "Hello User!";
	}


}