package com.revature.P2API.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.P2API.repository.models.Song;
import com.revature.P2API.service.SongService;

@RestController
@RequestMapping(path="/songs")
public class SongController {

	private final RestTemplate restTemplate;
	Object result = null;
	ObjectMapper mapper = new ObjectMapper();
	
	private final SongService songService;
	
	@Autowired
	public SongController(SongService songService) {
		this.songService = songService;
		this.restTemplate = new RestTemplate();
	}

	@RequestMapping(value = "/song/album/{id}", method = RequestMethod.GET)
	public @ResponseBody Object getSongsByAlbumId(@PathVariable String id) throws IOException {

		String response = restTemplate.getForObject("https://www.theaudiodb.com/api/v1/json/2/track.php?m=" + id,
				String.class);

		if (response.equals("{\"track\":null}"))
			result = response;

		else {

			String responseFormatted = response.substring(9, response.length() - 1);

			result = (Song[]) mapper.readValue(responseFormatted, new TypeReference<Song[]>() {
			});

		}

		return result;

	}

	@RequestMapping(value = "/song/id/{id}", method = RequestMethod.GET)
	public @ResponseBody Object getSongById(@PathVariable String id)
			throws JsonMappingException, JsonProcessingException {

		String response = restTemplate.getForObject("https://www.theaudiodb.com/api/v1/json/2/track.php?h=" + id,
				String.class);

		if (response.equals("{\"track\":null}"))
			result = response;

		else {
			String responseFormatted = response.substring(10, response.length() - 2);

			result = (Song) mapper.readValue(responseFormatted, new TypeReference<Song>() {
			});

		}

		return result;

	}
	
	@PostMapping("/create")
	public void createSong(@RequestBody Song song) {
		songService.createSong(song);
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteUser(@PathVariable long id) {
		songService.deleteSongById(id);
	}
	
	@GetMapping
	public List<Song> getSongs() {
		return songService.getSongs();
	}
	
}