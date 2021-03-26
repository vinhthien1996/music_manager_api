package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Genre;
import com.example.demo.service.GenreService;
import com.example.demo.vo.GenreVO;

@RestController
@RequestMapping("/api/genre")
public class GenreController {

	@Autowired
	GenreService service;
	@Autowired
	SimpMessagingTemplate template;

//	GET LIST GENRE
	@GetMapping
	public List<GenreVO> getGenre() {
		return service.getAllGenre();
	}

//	GET GENRE BY ID
	@GetMapping("/{id}")
	public Genre getGenreById(@PathVariable int id) {
		return service.getGenreById(id);
	}

//	CREATE GENRE
	@PostMapping
	public ResponseEntity saveGenre(@RequestBody Genre genre) {
		Genre data = service.createGenre(genre);
		if (data == null) {
			return ResponseEntity.status(409).body("Genre name already exists!");
		}
		template.convertAndSend("/topic/genre", true);
		template.convertAndSend("/topic/song", true);
		return ResponseEntity.status(200).body(data);
	}

//	UPDATE GENRE
	@PutMapping("/{id}")
	public ResponseEntity updaGenre(@PathVariable int id, @RequestBody Genre genre) {
		genre.setGenre_id(id);
		Genre data = service.updateGenre(genre);
		JSONObject message = new JSONObject();
		if (data == null) {
			return ResponseEntity.status(409).body("Genre name already exists!");
		}
		template.convertAndSend("/topic/genre", true);
		template.convertAndSend("/topic/song", true);
		return ResponseEntity.status(200).body(data);
	}

//	DELETE GENRE
	@DeleteMapping("/{id}")
	public ResponseEntity deleteProduct(@PathVariable int id) {
		service.deleteGenre(id);
		Map<String, String> message = new HashMap<String, String>();
		message.put("message", "Genre deleted successfully");
		template.convertAndSend("/topic/genre", true);
		return ResponseEntity.status(200).body(message);
	}
}
