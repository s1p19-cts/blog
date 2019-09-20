package com.cts.reader.blogreader.service;

import com.cts.reader.blogreader.model.Config;
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

	public String getGreeting(String username) {
		return new RestTemplate()
				       .getForObject(configs.getPrimaryEndpoint() + "/greeting/{username}",
						       String.class, username);
	}

	/**
	 * Get blogs from the given endpoitn
	 *
	 * @return
	 */
	public Iterable getBlogs() {
		Iterable blogs = null;

		RestTemplate restTemplate = new RestTemplate();
		log.info(" ... initiated restTemplate - {}", restTemplate);
		ResponseEntity<Iterable<Map>> response = restTemplate.exchange(
				configs.getPrimaryEndpoint() + "/blogs",
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<Iterable<Map>>() {
				});
		blogs = response.getBody();
		return blogs;
	}

	/**
	 * Get blog for given blog id, from the endpoint.
	 *
	 * @param id
	 * @return
	 */
	public Map getBlogById(String id) {
		Map blog = null;

		RestTemplate restTemplate = new RestTemplate();
		log.info(" ... initiated restTemplate - {}", restTemplate);

		//		HttpHeaders headers = new HttpHeaders();
		//		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		//		HttpEntity<String> entity = new HttpEntity<>(headers);

		Map<String, String> params = new HashMap<String, String>();
		params.put("id", id);

		ResponseEntity<Map> response = restTemplate.exchange(
				configs.getPrimaryEndpoint() + "/blog/{id}",
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<Map>() {
				}, params);
		blog = response.getBody();
		return blog;
	}
}