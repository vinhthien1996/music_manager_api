package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

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

	@Override
	public List<SongFullVO> getAllSong() {
		String sql = "SELECT song.song_id, song.song_name, song.release_time, genre.genre_name, musician.musician_name, singer.singer_name, song.favorite FROM song JOIN genre ON (song.genre_id = genre.genre_id) JOIN musician ON (song.musician_id = musician.musician_id) JOIN singer ON (song.singer_id = singer.singer_id) ORDER BY song.song_id DESC";
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
	public Song createSong(SongFullVO songvo) {
		
		Genre genre = genreRepo.findGenreByName(songvo.getGenre_name());
		Musician musician = musicianRepo.findMusicianByName(songvo.getMusician_name());
		Singer singer = singerRepo.findSingerByName(songvo.getSinger_name());
		
		Song song = new Song();
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
	public Song updateSong(SongFullVO songvo) {
		Song find = repository.findSongByName(songvo.getSong_name());
		Song findMySong = repository.findById(songvo.getSong_id()).get();
		if (find != null && find.getSong_name() != findMySong.getSong_name()) {
			return null;
		}
		
		Genre genre = genreRepo.findGenreByName(songvo.getGenre_name());
		Musician musician = musicianRepo.findMusicianByName(songvo.getMusician_name());
		Singer singer = singerRepo.findSingerByName(songvo.getSinger_name());
		
		Song song = new Song();
		song.setSong_id(songvo.getSong_id());
		song.setSong_name(songvo.getSong_name());
		song.setGenre(genre);
		song.setMusician(musician);
		song.setSinger(singer);
		song.setRelease_time(songvo.getRelease_time());
		song.setFavorite(songvo.getFavorite());
		
		return repository.save(song);
	}

	@Override
	public void deleteSong(int id) {
		
		Song vo = getSongById(id);
		if (!vo.getFavorite()) {
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
		String sql = "SELECT song.song_id, song.song_name, song.release_time, genre.genre_name, musician.musician_name, singer.singer_name, song.favorite FROM song JOIN genre ON (song.genre_id = genre.genre_id) JOIN musician ON (song.musician_id = musician.musician_id) JOIN singer ON (song.singer_id = singer.singer_id) WHERE song.song_id = " + id;
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

		String sql = "SELECT song.song_id, song.song_name, song.release_time, genre.genre_name, musician.musician_name, singer.singer_name, song.favorite FROM song JOIN genre ON (song.genre_id = genre.genre_id) JOIN musician ON (song.musician_id = musician.musician_id) JOIN singer ON (song.singer_id = singer.singer_id) WHERE favorite = 1 ORDER BY song.song_id DESC";
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
