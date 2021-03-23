package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Singer;
import com.example.demo.vo.SingerVO;

public interface SingerService {

	public List<SingerVO> getAllSinger();

	public Singer getSingerById(int id);
	
	public Singer getSingerByName(String name);
	
	public SingerVO getSingerSongById(int id);
	
	public List<SingerVO> getSingerPage(int page, int limit);

	public Singer createSinger(Singer singer);

	public Singer updateSinger(Singer singer);

	public void deleteSinger(int id);
	
	public int countSinger(int limit);

}
