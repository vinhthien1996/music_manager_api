package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Genre;
import com.example.demo.repository.GenreRepository;
import com.example.demo.vo.GenreVO;

@Service
public class GenreServiceImpl implements GenreService {

	@Autowired
	GenreRepository repository;
	@Autowired
    private JdbcTemplate jdbcTemplate;

	@Override
	public List<GenreVO> getAllGenre() {
		String sql = "SELECT genre.genre_id, genre.genre_name, COUNT(song.genre_id) AS song_num FROM genre LEFT JOIN song ON (genre.genre_id = song.genre_id) GROUP BY genre.genre_id ORDER BY genre_id DESC";
		List<GenreVO> vo = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(GenreVO.class));
		if(vo.size() == 0)
			return null;
		return vo;
	}

	@Override
	public Genre getGenreById(int id) {
		return repository.findById(id).get();
	}

	@Override
	public Genre createGenre(Genre genre) {
		Genre find = repository.findGenreByName(genre.getGenre_name());
		if (find != null) {
			return null;
		}
		return repository.save(genre);
	}

	@Override
	public Genre updateGenre(Genre genre) {
		Genre find = repository.findGenreByName(genre.getGenre_name());
		Genre findMyGenre = repository.findById(genre.getGenre_id()).get();
		if (find != null && find.getGenre_name() != findMyGenre.getGenre_name()) {
			return null;
		}
		return repository.save(genre);
	}

	@Override
	public void deleteGenre(int id) {
		GenreVO vo = getGenreSongById(id);
		if (vo.getSong_num() == 0) {
			repository.deleteById(id);
		}
	}

	@Override
	public Genre getGenreByName(String genre_name) {
		return repository.findGenreByName(genre_name);
	}

	@Override
	public List<GenreVO> getGenrePage(int page, int limit) {
		int num = (page - 1) * limit;
		
		String sql = "SELECT genre.genre_id, genre.genre_name, COUNT(song.genre_id) AS song_num FROM genre LEFT JOIN song ON (genre.genre_id = song.genre_id) GROUP BY genre.genre_id ORDER BY genre_id DESC LIMIT " + num + ", " + limit;
		List<GenreVO> vo = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(GenreVO.class));
		if(vo.size() == 0)
			return null;
		return vo;
	}

	@Override
	public int countGenre(int limit) {
		int num = repository.countGenre() / limit + ((repository.countGenre() % limit == 0) ? 0 : 1); 
		return num;
	}

	@Override
	public GenreVO getGenreSongById(int id) {
		String sql = "SELECT genre.genre_id, genre.genre_name, COUNT(song.genre_id) AS song_num FROM genre LEFT JOIN song ON (genre.genre_id = song.genre_id) WHERE genre.genre_id = " + id;
		GenreVO vo = (GenreVO) jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(GenreVO.class));
		if (vo == null)
			return null;
		return vo;
	}

}
