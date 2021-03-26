package com.example.demo.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Genre;
import com.example.demo.entity.Musician;
import com.example.demo.entity.Singer;
import com.example.demo.entity.Song;
import com.example.demo.repository.GenreRepository;
import com.example.demo.repository.MusicianRepository;
import com.example.demo.repository.SingerRepository;
import com.example.demo.repository.SongRepository;
import com.example.demo.vo.SongFullVO;
import com.example.demo.vo.SongVO;

@Service
public class SongServiceImpl implements SongService {

	@Autowired
	SongRepository repository;
	@Autowired
	GenreRepository genreRepo;
	@Autowired
	MusicianRepository musicianRepo;
	@Autowired
	SingerRepository singerRepo;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Value("${upload.path}")
	private String fileUpload;

	@Override
	public List<SongFullVO> getAllSong() {
		String sql = "SELECT song.song_id, song.song_name, song.release_time, genre.genre_name, musician.musician_name, singer.singer_name, song.favorite, song.url FROM song JOIN genre ON (song.genre_id = genre.genre_id) JOIN musician ON (song.musician_id = musician.musician_id) JOIN singer ON (song.singer_id = singer.singer_id) ORDER BY song.song_id DESC";
		List<SongFullVO> vo = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(SongFullVO.class));
		if (vo.size() == 0)
			return null;
		return vo;
	}

	@Override
	public Song getSongById(int id) {
		return repository.findById(id).get();
	}

	@Override
	public Song createSong(SongFullVO songvo, MultipartFile file) {
		
		Song song = new Song();
		
		if(file != null && file.getContentType().equals("audio/mpeg") && file.getSize() <= 20971520) {			
			try {
				Date date = new Date();
				long now = date.getTime();
				FileCopyUtils.copy(file.getBytes(), new File(this.fileUpload + "/music/" + now + "_" + file.getOriginalFilename()));
				song.setUrl(now + "_" + file.getOriginalFilename());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		Genre genre = genreRepo.findGenreByName(songvo.getGenre_name());
		Musician musician = musicianRepo.findMusicianByName(songvo.getMusician_name());
		Singer singer = singerRepo.findSingerByName(songvo.getSinger_name());
		
		song.setSong_name(songvo.getSong_name());
		song.setGenre(genre);
		song.setMusician(musician);
		song.setSinger(singer);
		song.setRelease_time(songvo.getRelease_time());
		
		Song find = repository.findSongByName(song.getSong_name());
		if (find != null) {
			return null;
		}
		
		return repository.save(song);
	}

	@Override
	public Song updateSong(SongFullVO songvo, MultipartFile file) {
//		FIND NAME EXISTS
		Song find = repository.findSongByName(songvo.getSong_name());
//		FIND OLD NAME
		Song findMySong = repository.findById(songvo.getSong_id()).get();
//		CHECK SONG NAME NOT EXISTS AND SONG NAME DEFFIRENCE OLD NAME 
		if (find != null && find.getSong_name() != findMySong.getSong_name()) {
			return null;
		}
		
		Song song = new Song();
		
//		GET OLD URL
		song.setUrl(findMySong.getUrl());
		
//		UPLOAD FILE
		if(file != null && file.getContentType().equals("audio/mpeg") && file.getSize() <= 20971520) {			
			try {
				Date date = new Date();
				long now = date.getTime();
				FileCopyUtils.copy(file.getBytes(), new File(this.fileUpload + "/music/" + now + "_" + file.getOriginalFilename()));
				
//				DELETE OLD MP3 FILE
				File deleteFile = new File("./src/main/resources/static/music/" + findMySong.getUrl());
				if(deleteFile.exists()){
					deleteFile.delete();
				}
				
				song.setUrl(now + "_" + file.getOriginalFilename());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		Genre genre = genreRepo.findGenreByName(songvo.getGenre_name());
		Musician musician = musicianRepo.findMusicianByName(songvo.getMusician_name());
		Singer singer = singerRepo.findSingerByName(songvo.getSinger_name());
	
		song.setSong_id(songvo.getSong_id());
		song.setSong_name(songvo.getSong_name());
		song.setGenre(genre);
		song.setMusician(musician);
		song.setSinger(singer);
		song.setRelease_time(songvo.getRelease_time());
		song.setFavorite(findMySong.getFavorite());
		
		return repository.save(song);
	}

	@Override
	public void deleteSong(int id) {
		
		Song vo = getSongById(id);
		if (!vo.getFavorite()) {
//			FIND SONG
			Song findMySong = repository.findById(id).get();
//			DELETE SONG FILE
			File deleteFile = new File("./src/main/resources/static/music/" + findMySong.getUrl());
			if(deleteFile.exists()){
				deleteFile.delete();
			}
			
			repository.deleteById(id);
		}
	}

	@Override
	public List<SongVO> getSongBySingerId(int id) {

		String sql = "SELECT song.song_id, song.song_name, song.release_time, song.favorite FROM song WHERE song.singer_id = " + id + " ORDER BY song.song_id DESC";
		List<SongVO> vo = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(SongVO.class));
		if (vo.size() == 0)
			return null;
		return vo;
	}

	@Override
	public List<SongFullVO> getSongPage(int page, int limit) {
		int num = (page - 1) * limit;

		String sql = "SELECT song.song_id, song.song_name, song.release_time, genre.genre_name, musician.musician_name, singer.singer_name, song.favorite FROM song JOIN genre ON (song.genre_id = genre.genre_id) JOIN musician ON (song.musician_id = musician.musician_id) JOIN singer ON (song.singer_id = singer.singer_id) ORDER BY song.song_id DESC LIMIT " + num + ", " + limit;
		List<SongFullVO> vo = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(SongFullVO.class));
		if (vo.size() == 0)
			return null;
		return vo;
	}

	@Override
	public int countSong(int limit) {
		int num = repository.countSong() / limit + ((repository.countSong() % limit == 0) ? 0 : 1);
		return num;
	}

	@Override
	public SongFullVO getSongFullById(int id) {
		String sql = "SELECT song.song_id, song.song_name, song.release_time, genre.genre_name, musician.musician_name, singer.singer_name, song.favorite, song.url FROM song JOIN genre ON (song.genre_id = genre.genre_id) JOIN musician ON (song.musician_id = musician.musician_id) JOIN singer ON (song.singer_id = singer.singer_id) WHERE song.song_id = " + id;
		SongFullVO vo = (SongFullVO) jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(SongFullVO.class));
		if (vo.getGenre_name() == null)
			return null;
		return vo;
	}

	@Override
	public int countSongBySinger(int limit, int id) {
		int num = repository.countSongBySinger(id) / limit + ((repository.countSongBySinger(id) % limit == 0) ? 0 : 1);
		return num;
	}

	@Override
	public void addFavoriteSong(int id) {
		Song song = getSongById(id);
		song.setFavorite(!song.getFavorite());
		repository.save(song);
	}

	@Override
	public List<SongFullVO> getSongFavorite() {

		String sql = "SELECT song.song_id, song.song_name, song.release_time, genre.genre_name, musician.musician_name, singer.singer_name, song.favorite, song.url FROM song JOIN genre ON (song.genre_id = genre.genre_id) JOIN musician ON (song.musician_id = musician.musician_id) JOIN singer ON (song.singer_id = singer.singer_id) WHERE favorite = 1 ORDER BY song.song_id DESC";
		List<SongFullVO> vo = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(SongFullVO.class));
		if (vo.size() == 0)
			return null;
		return vo;
	}

	@Override
	public int countSongFavorite(int limit) {
		int num = repository.countSongFavorite() / limit + ((repository.countSongFavorite() % limit == 0) ? 0 : 1);
		return num;
	}

}
