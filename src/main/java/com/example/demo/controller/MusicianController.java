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

import com.example.demo.entity.Musician;
import com.example.demo.service.MusicianService;
import com.example.demo.vo.MusicianVO;

@RestController
@RequestMapping("/api/musician")
public class MusicianController {

	@Autowired
	MusicianService service;
	@Autowired
	SimpMessagingTemplate template;

//	GET LIST MUSICIAN
	@GetMapping
	public List<MusicianVO> getMusician() {
		return service.getAllMusician();
	}

//	GET MUSICIAN BY ID
	@GetMapping("/{id}")
	public Musician getMusicianById(@PathVariable int id) {
		return service.getMusicianById(id);
	}

//	GET NUM PAGE BY LIMIT
	@GetMapping("/page/{limit}")
	public ResponseEntity countMusician(@PathVariable int limit) {
		int page = service.countMusician(limit);
		Map message = new HashMap<String, Integer>();
		message.put("page", page);
		return ResponseEntity.status(200).body(message);
	}
	
//	GET LIST MUSICIAN PAGINATION
	@GetMapping("/page/{page}/{limit}")
	public List<MusicianVO> getGenrePage(@PathVariable int page, @PathVariable int limit) {
		return service.getMusicianPage(page, limit);
	}

//	CREATE MUSICIAN
	@PostMapping
	public ResponseEntity saveMusician(@RequestBody Musician musician) {
		Musician data = service.createMusician(musician);
		if (data == null) {
			return ResponseEntity.status(409).body("Musician name already exists!");
		}
		template.convertAndSend("/topic/musician", true);
		template.convertAndSend("/topic/song", true);
		return ResponseEntity.status(200).body(data);
	}

//	UPDATE MUSICIAN
	@PutMapping("/{id}")
	public ResponseEntity updaMusician(@PathVariable int id, @RequestBody Musician musician) {
		musician.setMusician_id(id);
		Musician data = service.updateMusician(musician);
		JSONObject message = new JSONObject();
		if (data == null) {
			return ResponseEntity.status(409).body("Musician name already exists!");
		}
		template.convertAndSend("/topic/musician", true);
		template.convertAndSend("/topic/song", true);
		return ResponseEntity.status(200).body(data);
	}

//	DELETE MUSICIAN
	@DeleteMapping("/{id}")
	public ResponseEntity deleteMusician(@PathVariable int id) {
		service.deleteMusician(id);;
		Map<String, String> message = new HashMap<String, String>();
		message.put("message", "Musician deleted successfully");
		template.convertAndSend("/topic/musician", true);
		return ResponseEntity.status(200).body(message);
	}
}
