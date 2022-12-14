package com.revature.P2API.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.P2API.models.Artist;

@RestController
@RequestMapping("/artist")
@CrossOrigin("*")
public class ArtistController {
	Logger logger = LoggerFactory.getLogger(ArtistController.class);

	private final RestTemplate restTemplate;
	Object result = null;
	ObjectMapper mapper = new ObjectMapper();

	public ArtistController() {
		this.restTemplate = new RestTemplate();
	}

	@RequestMapping(value = "/name/{id}", method = RequestMethod.GET)
	public @ResponseBody Object getArtistByName(@PathVariable String id) throws IOException {

		String response = restTemplate.getForObject("https://www.theaudiodb.com/api/v1/json/523532/search.php?s=" + id,
				String.class);

		if (response.equals("{\"artists\":null}")) {
			logger.error("Unable to get artist by name.");
			result = response;
		}
		else {

			String responseFormatted = response.substring(12, response.length() - 2);
			result = (Artist) mapper.readValue(responseFormatted, new TypeReference<Artist>() {
			});

		}
		return result;
		

	}
	

	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	public @ResponseBody Object getArtistById(@PathVariable String id)
			throws JsonMappingException, JsonProcessingException {

		String response = restTemplate.getForObject("https://www.theaudiodb.com/api/v1/json/523532/artist.php?i=" + id,
				String.class);

		if (response.equals("{\"artists\":null}")) {
			logger.error("Unable to get artist by id");
			result = response;
		}
		else {

			String responseFormatted = response.substring(12, response.length() - 2);

			result = (Artist) mapper.readValue(responseFormatted, new TypeReference<Artist>() {
			});

		}

		return result;

	}
	
	

}
