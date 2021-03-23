package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Singer;
import com.example.demo.entity.Singer;
import com.example.demo.entity.Singer;
import com.example.demo.service.SingerService;
import com.example.demo.vo.SingerVO;

@RestController
@RequestMapping("/api/singer")
public class SingerController {
	
	@Autowired
	SingerService service;

	@GetMapping
	public List<SingerVO> getSinger() {
		return service.getAllSinger();
	}
	
	@GetMapping("/{id}")
	public Singer getSingerById(@PathVariable int id) {
		return service.getSingerById(id);
	}
	
	@GetMapping("/info/{id}")
	public SingerVO getSingerSongById(@PathVariable int id) {
		return service.getSingerSongById(id);
	}
	
	@GetMapping("/page/{limit}")
	public ResponseEntity countSinger(@PathVariable int limit) {
		int page = service.countSinger(limit);
		Map message = new HashMap<String, Integer>();
		message.put("page", page);
		return ResponseEntity.status(200).body(message);
	}	
	
	@GetMapping("/page/{page}/{limit}")
	public List<SingerVO> getGenrePage(@PathVariable int page, @PathVariable int limit) {
		return service.getSingerPage(page, limit);
	}
	
	@PostMapping
	public ResponseEntity saveSinger(@RequestBody Singer singer) {
		Singer data = service.createSinger(singer);
		if (data == null) {
			return ResponseEntity.status(409).body("Singer name already exists!");
		}
		return ResponseEntity.status(200).body(data);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity updateSinger(@PathVariable int id, @RequestBody Singer singer) {
		singer.setSinger_id(id);
		Singer data = service.updateSinger(singer);
		JSONObject message = new JSONObject();
		if (data == null) {
			return ResponseEntity.status(409).body("Singer name already exists!");
		}
		return ResponseEntity.status(200).body(data);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity deleteProduct(@PathVariable int id) {
		service.deleteSinger(id);
		Map<String, String> message = new HashMap<String, String>();
		message.put("message", "Singer deleted successfully");
		return ResponseEntity.status(200).body(message);
	}

}
