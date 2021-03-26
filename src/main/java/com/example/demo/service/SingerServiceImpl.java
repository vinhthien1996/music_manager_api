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

import com.example.demo.entity.Singer;
import com.example.demo.repository.SingerRepository;
import com.example.demo.vo.SingerVO;

@Service
public class SingerServiceImpl implements SingerService {

	@Autowired
	SingerRepository repository;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Value("${upload.path}")
	private String fileUpload;

	@Override
	public List<SingerVO> getAllSinger() {
		String sql = "SELECT singer.singer_id, singer.singer_name, singer.singer_sex, singer.singer_birthday, COUNT(song.singer_id) AS song_num FROM singer LEFT JOIN song ON (singer.singer_id = song.singer_id) GROUP BY singer.singer_id ORDER BY singer_id DESC";
		List<SingerVO> vo = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(SingerVO.class));
		if (vo.size() == 0)
			return null;
		return vo;
	}

	@Override
	public Singer getSingerById(int id) {
		return repository.findById(id).get();
	}

	@Override
	public Singer createSinger(Singer singer, MultipartFile file) {

		if (file != null
				&& (file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/jpg")
						|| file.getContentType().equals("image/png") || file.getContentType().equals("image/gif"))
				&& file.getSize() <= 20971520) {
			try {
				Date date = new Date();
				long now = date.getTime();
				FileCopyUtils.copy(file.getBytes(),
						new File(this.fileUpload + "/images/" + now + "_" + file.getOriginalFilename()));
				singer.setSinger_avatar(now + "_" + file.getOriginalFilename());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		Singer find = repository.findSingerByName(singer.getSinger_name());
		if (find != null) {
			return null;
		}

		return repository.save(singer);
	}

	@Override
	public Singer updateSinger(Singer singer, MultipartFile file) {
		Singer find = repository.findSingerByName(singer.getSinger_name());
		Singer findMySinger = repository.findById(singer.getSinger_id()).get();
		if (find != null && find.getSinger_name() != findMySinger.getSinger_name()) {
			return null;
		}
		
		singer.setSinger_avatar(findMySinger.getSinger_avatar());

//		UPLOAD FILE
		if (file != null
				&& (file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/jpg")
						|| file.getContentType().equals("image/png") || file.getContentType().equals("image/gif"))
				&& file.getSize() <= 20971520) {
			try {
				Date date = new Date();
				long now = date.getTime();
				FileCopyUtils.copy(file.getBytes(),
						new File(this.fileUpload + "/images/" + now + "_" + file.getOriginalFilename()));

//				DELETE OLD MP3 FILE
				if (findMySinger.getSinger_avatar() != null) {
					File deleteFile = new File("./src/main/resources/static/images/" + findMySinger.getSinger_avatar());
					if (deleteFile.exists()) {
						deleteFile.delete();
					}
				}

				singer.setSinger_avatar(now + "_" + file.getOriginalFilename());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return repository.save(singer);
	}

	@Override
	public void deleteSinger(int id) {
		SingerVO vo = getSingerSongById(id);
		if (vo.getSong_num() == 0) {
//			FIND SINGER
			Singer findMySong = repository.findById(id).get();
//			DELETE SONG FILE
			if (findMySong.getSinger_avatar() != null) {
				File deleteFile = new File("./src/main/resources/static/images/" + findMySong.getSinger_avatar());
				if (deleteFile.exists()) {
					deleteFile.delete();
				}
			}

			repository.deleteById(id);
		}
	}

	@Override
	public List<SingerVO> getSingerPage(int page, int limit) {
		int num = (page - 1) * limit;

		String sql = "SELECT singer.singer_id, singer.singer_name, singer.singer_sex, singer.singer_birthday, COUNT(song.singer_id) AS song_num FROM singer LEFT JOIN song ON (singer.singer_id = song.singer_id) GROUP BY singer.singer_id ORDER BY singer_id DESC LIMIT "
				+ num + ", " + limit;
		List<SingerVO> vo = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(SingerVO.class));
		if (vo.size() == 0)
			return null;
		return vo;
	}

	@Override
	public int countSinger(int limit) {
		int num = repository.countSinger() / limit + ((repository.countSinger() % limit == 0) ? 0 : 1);
		return num;
	}

	@Override
	public SingerVO getSingerSongById(int id) {
		String sql = "SELECT singer.singer_id, singer.singer_name, singer.singer_sex, singer.singer_birthday, COUNT(song.singer_id) AS song_num FROM singer LEFT JOIN song ON (singer.singer_id = song.singer_id) WHERE singer.singer_id = "
				+ id;
		SingerVO vo = (SingerVO) jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(SingerVO.class));
		if (vo == null)
			return null;
		return vo;
	}

	@Override
	public Singer getSingerByName(String name) {
		return repository.findSingerByName(name);
	}

}
