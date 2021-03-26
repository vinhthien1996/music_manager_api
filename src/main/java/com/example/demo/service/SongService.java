package com.example.demo.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Song;
import com.example.demo.vo.SongFullVO;
import com.example.demo.vo.SongVO;

public interface SongService {

	public List<SongFullVO> getAllSong();

	public Song getSongById(int id);

	public SongFullVO getSongFullById(int id);

	public Song createSong(SongFullVO song, MultipartFile file);

	public Song updateSong(SongFullVO songvo, MultipartFile file);

	public void deleteSong(int id);

	public List<SongVO> getSongBySingerId(int id);

	public List<SongFullVO> getSongPage(int page, int limit);

	public int countSong(int limit);

	public int countSongBySinger(int limit, int id);

	public void addFavoriteSong(int id);

	public List<SongFullVO> getSongFavorite();

	public int countSongFavorite(int limit);
}
