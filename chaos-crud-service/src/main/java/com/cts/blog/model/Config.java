package com.cts.blog.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
//@ConfigurationProperties(prefix = "config")
@Setter
@Getter
@NoArgsConstructor
public class Config {

	@Value("${config.name}")
	private String name;
	@Value("${config.info}")
	private String info;
	@Value("${config.region}")
	private String region;
}