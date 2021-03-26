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

//	GET LIST SONG
	@GetMapping
	public List<SongFullVO> getSong() {
		return service.getAllSong();
	}

//	GET SONG BY ID
	@GetMapping("/{id}")
	public Song getSongById(@PathVariable int id) {
		return service.getSongById(id);
	}

//	GET SONG FULL BY ID
	@GetMapping("/full/{id}")
	public SongFullVO getSongFullById(@PathVariable int id) {
		return service.getSongFullById(id);
	}

//	GET NUM PAGE BY LIMIT 
	@GetMapping("/page/{limit}")
	public ResponseEntity countMusician(@PathVariable int limit) {
		int page = service.countSong(limit);
		Map message = new HashMap<String, Integer>();
		message.put("page", page);
		return ResponseEntity.status(200).body(message);
	}

//	GET NUM PAGE SONG FAVORITE BY LIMIT
	@GetMapping("/page-favorite/{limit}")
	public ResponseEntity countSongFavorite(@PathVariable int limit) {
		int page = service.countSongFavorite(limit);
		Map message = new HashMap<String, Integer>();
		message.put("page", page);
		return ResponseEntity.status(200).body(message);
	}

//	GET NUM PAGE SONG BY ID SINGER AND LIMIT
	@GetMapping("/page-id/{id}/{limit}")
	public ResponseEntity countSongBySinger(@PathVariable int id, @PathVariable int limit) {
		int page = service.countSongBySinger(limit, id);
		Map message = new HashMap<String, Integer>();
		message.put("page", page);
		return ResponseEntity.status(200).body(message);
	}

//	GET LIST SONG PAGINATION
	@GetMapping("/page/{page}/{limit}")
	public List<SongFullVO> getGenrePage(@PathVariable int page, @PathVariable int limit) {
		return service.getSongPage(page, limit);
	}

//	GET LIST SONG FAVORITE
	@GetMapping("/favorite")
	public List<SongFullVO> getSongFavorite() {
		return service.getSongFavorite();
	}

//	GET LIST SONG BY SINGER ID
	@GetMapping("/singer/{id}")
	public List<SongVO> getSongSingerById(@PathVariable int id) {
		return service.getSongBySingerId(id);
	}

//	CREATE SONG
	@PostMapping(consumes = { "multipart/form-data" })
	public ResponseEntity saveSong(
				@RequestParam(value = "file", required = false) MultipartFile file,
				@RequestParam("song_name") String song_name,
				@RequestParam("release_time") Timestamp release_time,
				@RequestParam("genre_name") String genre_name,
				@RequestParam("musician_name") String musician_name,
				@RequestParam("singer_name") String singer_name
				) {
		SongFullVO vo = new SongFullVO();
		vo.setSong_name(song_name);
		vo.setRelease_time(release_time);
		vo.setGenre_name(genre_name);
		vo.setMusician_name(musician_name);
		vo.setSinger_name(singer_name);
		
		Song data = service.createSong(vo, file);
		if (data == null) {
			return ResponseEntity.status(409).body("Song name already exists!");
		}
		template.convertAndSend("/topic/song", true);
		template.convertAndSend("/topic/genre", true);
		template.convertAndSend("/topic/musician", true);
		template.convertAndSend("/topic/singer", true);
		return ResponseEntity.status(200).body(data);
	}

//	UPDATE SONG
	@PutMapping(value = "/{id}", consumes = { "multipart/form-data" })
	public ResponseEntity updaSong(
			@PathVariable int id,
			@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam("song_name") String song_name,
			@RequestParam("release_time") Timestamp release_time,
			@RequestParam("genre_name") String genre_name,
			@RequestParam("musician_name") String musician_name,
			@RequestParam("singer_name") String singer_name) {
		SongFullVO vo = new SongFullVO();
		vo.setSong_name(song_name);
		vo.setRelease_time(release_time);
		vo.setGenre_name(genre_name);
		vo.setMusician_name(musician_name);
		vo.setSinger_name(singer_name);
		
		vo.setSong_id(id);
		Song data = service.updateSong(vo, file);

		JSONObject message = new JSONObject();
		if (data == null) {
			return ResponseEntity.status(409).body("Song name already exists!");
		}
		template.convertAndSend("/topic/song", true);
		template.convertAndSend("/topic/genre", true);
		template.convertAndSend("/topic/musician", true);
		template.convertAndSend("/topic/singer", true);
		return ResponseEntity.status(200).body(data);
	}

//	DELETE SONG
	@DeleteMapping("/{id}")
	public ResponseEntity deleteProduct(@PathVariable int id) {
		service.deleteSong(id);
		Map<String, String> message = new HashMap<String, String>();
		message.put("message", "Song deleted successfully");
		template.convertAndSend("/topic/song", true);
		template.convertAndSend("/topic/genre", true);
		template.convertAndSend("/topic/musician", true);
		template.convertAndSend("/topic/singer", true);
		return ResponseEntity.status(200).body(message);
	}

//	ADD AND REMOVE FAVORITE
	@GetMapping("/favorite/{id}")
	public void addFavoriteSong(@PathVariable int id) {
		service.addFavoriteSong(id);
		template.convertAndSend("/topic/song", true);
	}
}
