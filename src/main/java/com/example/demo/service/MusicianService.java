package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Musician;
import com.example.demo.vo.MusicianVO;

public interface MusicianService {

	public List<MusicianVO> getAllMusician();

	public Musician getMusicianById(int id);
	
	public Musician getMusicianByName(String name);
	
	public MusicianVO getMusicianSongById(int id);
	
	public List<MusicianVO> getMusicianPage(int page, int limit);

	public Musician createMusician(Musician musician);

	public Musician updateMusician(Musician musician);

	public void deleteMusician(int id);
	
	public int countMusician(int limit);

}
