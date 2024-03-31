package com.example.demo;

import java.util.List;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class DemoApplication {

	private final String apiKey = "5e98109ae7mshb51b2dc83b93cb5p182f50jsn868e8efaa3b8";

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	public Map<String, List<String>> getInfo() {
		String url = "https://omgvamp-hearthstone-v1.p.rapidapi.com/info";
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-RapidAPI-Key", apiKey);

		HttpEntity<String> entity = new HttpEntity<>(headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

		if (response.getStatusCode().is2xxSuccessful()) {
			try {
				ObjectMapper mapper = new ObjectMapper();
				Map<String, Object> fullResponse = mapper.readValue(response.getBody(), Map.class);
				List<String> sets = (List<String>) fullResponse.get("sets");
				return Map.of("sets", sets);
			} catch (Exception e) {
				System.out.println("Error parsing JSON: " + e.getMessage());
			}
		} else {
			System.out.println("Error accessing Info Endpoint. Status code: " + response.getStatusCodeValue());
		}
		return Map.of(); // Return an empty map in case of failure
	}

	
}
