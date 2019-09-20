package com.cts.reader.blogreader.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Setter @Getter @NoArgsConstructor
public class Config {

	@Value("${config.name}")
	private String name;

	@Value("${config.info}")
	private String info;

	@Value("${config.region}")
	private String region;

	@Value("${blog.primary.endpoint}")
	private String primaryEndpoint;

	@Value("${blog.secondary.endpoint}")
	private String secondaryEndpoint;

	public String getServerPort() {
		return System.getProperty("server.port");
	}
}