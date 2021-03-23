package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Genre;
import com.example.demo.vo.GenreVO;

public interface GenreService {
	
	public List<GenreVO> getAllGenre();
	
	public Genre getGenreById(int id);
	
	public GenreVO getGenreSongById(int id);
	
	public Genre getGenreByName(String name);
	
	public List<GenreVO> getGenrePage(int page, int limit);
	
	public Genre createGenre(Genre genre);
	
	public Genre updateGenre(Genre genre);
	
	public void deleteGenre(int id);
	
	public int countGenre(int limit);
}
