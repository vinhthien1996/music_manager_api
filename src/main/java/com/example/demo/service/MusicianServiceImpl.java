package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Musician;
import com.example.demo.repository.MusicianRepository;
import com.example.demo.vo.MusicianVO;

@Service
public class MusicianServiceImpl implements MusicianService {

	@Autowired
	MusicianRepository repository;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<MusicianVO> getAllMusician() {
		
		String sql = "SELECT musician.musician_id, musician.musician_name, musician.musician_sex, musician.musician_birthday, COUNT(song.musician_id) AS song_num FROM musician LEFT JOIN song ON (musician.musician_id = song.musician_id) GROUP BY musician.musician_id ORDER BY musician_id DESC";
		List<MusicianVO> vo = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(MusicianVO.class));
		if (vo.size() == 0)
			return null;
		return vo;
	}

	@Override
	public Musician getMusicianById(int id) {
		return repository.findById(id).get();
	}

	@Override
	public Musician createMusician(Musician musician) {
		Musician find = repository.findMusicianByName(musician.getMusician_name());
		if (find != null) {
			return null;
		}

		return repository.save(musician);
	}

	@Override
	public Musician updateMusician(Musician musician) {
		Musician find = repository.findMusicianByName(musician.getMusician_name());
		Musician findMyMusician = repository.findById(musician.getMusician_id()).get();
		if (find != null && find.getMusician_name() != findMyMusician.getMusician_name()) {
			return null;
		}
		return repository.save(musician);
	}

	@Override
	public void deleteMusician(int id) {
		MusicianVO vo = getMusicianSongById(id);
		if (vo.getSong_num() == 0) {
			repository.deleteById(id);
		}
	}

	@Override
	public List<MusicianVO> getMusicianPage(int page, int limit) {
		int num = (page - 1) * limit;

		String sql = "SELECT musician.musician_id, musician.musician_name, musician.musician_sex, musician.musician_birthday, COUNT(song.musician_id) AS song_num FROM musician LEFT JOIN song ON (musician.musician_id = song.musician_id) GROUP BY musician.musician_id ORDER BY musician_id DESC LIMIT " + num + ", " + limit;
		List<MusicianVO> vo = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(MusicianVO.class));
		if (vo.size() == 0)
			return null;
		return vo;
	}

	@Override
	public int countMusician(int limit) {
		int num = repository.countMusician() / limit + ((repository.countMusician() % limit == 0) ? 0 : 1); 
		return num;
	}

	@Override
	public MusicianVO getMusicianSongById(int id) {
		String sql = "SELECT musician.musician_id, musician.musician_name, musician.musician_sex, musician.musician_birthday, COUNT(song.musician_id) AS song_num FROM musician LEFT JOIN song ON (musician.musician_id = song.musician_id) WHERE musician.musician_id = " + id;
		MusicianVO vo = (MusicianVO) jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(MusicianVO.class));
		if (vo == null)
			return null;
		return vo;
	}

	@Override
	public Musician getMusicianByName(String name) {
		return repository.findMusicianByName(name);
	}

}
