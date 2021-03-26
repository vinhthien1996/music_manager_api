package com.example.demo.controller;

import java.sql.Timestamp;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Singer;
import com.example.demo.service.SingerService;
import com.example.demo.vo.SingerVO;

@RestController
@RequestMapping("/api/singer")
public class SingerController {

	@Autowired
	SingerService service;
	@Autowired
	SimpMessagingTemplate template;

//	GET LIST SINGER
	@GetMapping
	public List<SingerVO> getSinger() {
		return service.getAllSinger();
	}

//	GET SINGER BY ID
	@GetMapping("/{id}")
	public Singer getSingerById(@PathVariable int id) {
		return service.getSingerById(id);
	}

//	GET INFO SINGER
	@GetMapping("/info/{id}")
	public SingerVO getSingerSongById(@PathVariable int id) {
		return service.getSingerSongById(id);
	}

//	GET NUM PAGE BY LIMIT
	@GetMapping("/page/{limit}")
	public ResponseEntity countSinger(@PathVariable int limit) {
		int page = service.countSinger(limit);
		Map message = new HashMap<String, Integer>();
		message.put("page", page);
		return ResponseEntity.status(200).body(message);
	}

//	GET LIST SINGER PAGINATION
	@GetMapping("/page/{page}/{limit}")
	public List<SingerVO> getGenrePage(@PathVariable int page, @PathVariable int limit) {
		return service.getSingerPage(page, limit);
	}

//	CREATE SINGER
	@PostMapping(consumes = { "multipart/form-data" })
	public ResponseEntity saveSinger(@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam("singer_name") String singer_name, @RequestParam("singer_sex") String singer_sex,
			@RequestParam(value = "singer_description", required = false) String singer_description,
			@RequestParam("singer_birthday") Timestamp singer_birthday) {

		Singer singer = new Singer();
		singer.setSinger_name(singer_name);
		singer.setSinger_sex(singer_sex);
		singer.setSinger_description(singer_description);
		singer.setSinger_birthday(singer_birthday);

		Singer data = service.createSinger(singer, file);

		if (data == null) {
			return ResponseEntity.status(409).body("Singer name already exists!");
		}
		template.convertAndSend("/topic/singer", true);
		template.convertAndSend("/topic/song", true);
		return ResponseEntity.status(200).body(data);
	}

//	UPDATE SINGER
	@PutMapping(value = "/{id}", consumes = { "multipart/form-data" })
	public ResponseEntity updateSinger(@PathVariable int id,
			@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam("singer_id") String singer_id,
			@RequestParam("singer_name") String singer_name,
			@RequestParam("singer_sex") String singer_sex,
			@RequestParam(value = "singer_description", required = false) String singer_description,
			@RequestParam("singer_birthday") Timestamp singer_birthday) {

		Singer singer = new Singer();
		singer.setSinger_id(id);
		singer.setSinger_name(singer_name);
		singer.setSinger_sex(singer_sex);
		singer.setSinger_description(singer_description);
		singer.setSinger_birthday(singer_birthday);

		Singer data = service.updateSinger(singer, file);
		JSONObject message = new JSONObject();
		if (data == null) {
			return ResponseEntity.status(409).body("Singer name already exists!");
		}
		template.convertAndSend("/topic/singer", true);
		template.convertAndSend("/topic/song", true);
		return ResponseEntity.status(200).body(data);
	}

//	DELETE SINGER
	@DeleteMapping("/{id}")
	public ResponseEntity deleteProduct(@PathVariable int id) {
		service.deleteSinger(id);
		Map<String, String> message = new HashMap<String, String>();
		message.put("message", "Singer deleted successfully");
		template.convertAndSend("/topic/singer", true);
		return ResponseEntity.status(200).body(message);
	}

}
