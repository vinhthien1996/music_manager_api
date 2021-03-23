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

import com.example.demo.entity.Song;
import com.example.demo.service.SongService;
import com.example.demo.vo.SongFullVO;
import com.example.demo.vo.SongVO;

@RestController
@RequestMapping("/api/song")
public class SongController {

	@Autowired
	SongService service;
	@Autowired
	SimpMessagingTemplate template;

	@GetMapping
	public List<SongFullVO> getSong() {
		return service.getAllSong();
	}
	
	@GetMapping("/{id}")
	public Song getSongById(@PathVariable int id) {
		return service.getSongById(id);
	}
	
	@GetMapping("/full/{id}")
	public SongFullVO getSongFullById(@PathVariable int id) {
		return service.getSongFullById(id);
	}
	
	@GetMapping("/page/{limit}")
	public ResponseEntity countMusician(@PathVariable int limit) {
		int page = service.countSong(limit);
		Map message = new HashMap<String, Integer>();
		message.put("page", page);
		return ResponseEntity.status(200).body(message);
	}
	
	@GetMapping("/page-favorite/{limit}")
	public ResponseEntity countSongFavorite(@PathVariable int limit) {
		int page = service.countSongFavorite(limit);
		Map message = new HashMap<String, Integer>();
		message.put("page", page);
		return ResponseEntity.status(200).body(message);
	}
	
	@GetMapping("/page-id/{id}/{limit}")
	public ResponseEntity countSongBySinger(@PathVariable int id, @PathVariable int limit) {
		int page = service.countSongBySinger(limit, id);
		Map message = new HashMap<String, Integer>();
		message.put("page", page);
		return ResponseEntity.status(200).body(message);
	}
	
	@GetMapping("/page/{page}/{limit}")
	public List<SongFullVO> getGenrePage(@PathVariable int page, @PathVariable int limit) {
		return service.getSongPage(page, limit);
	}
	
	@GetMapping("/favorite")
	public List<SongFullVO> getSongFavorite() {
		return service.getSongFavorite();
	}
	
	@GetMapping("/singer/{id}")
	public List<SongVO> getSongSingerById(@PathVariable int id) {
		return service.getSongBySingerId(id);
	}
	
	@PostMapping
	public ResponseEntity saveSong(@RequestBody SongFullVO song) {
		Song data = service.createSong(song);
		if (data == null) {
			return ResponseEntity.status(409).body("Song name already exists!");
		}
		template.convertAndSend("/topic/song", true);
		return ResponseEntity.status(200).body(data);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity updaSong(@PathVariable int id, @RequestBody SongFullVO songvo) {
		songvo.setSong_id(id);
		Song data = service.updateSong(songvo);
		
		JSONObject message = new JSONObject();
		if (data == null) {
			return ResponseEntity.status(409).body("Song name already exists!");
		}
		template.convertAndSend("/topic/song", true);
		return ResponseEntity.status(200).body(data);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity deleteProduct(@PathVariable int id) {
		service.deleteSong(id);
		Map<String, String> message = new HashMap<String, String>();
		message.put("message", "Song deleted successfully");
		template.convertAndSend("/topic/song", true);
		return ResponseEntity.status(200).body(message);
	}
	
	@GetMapping("/favorite/{id}")
	public void addFavoriteSong(@PathVariable int id) {
		service.addFavoriteSong(id);
		template.convertAndSend("/topic/song", true);
	}
}
