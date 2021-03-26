package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Genre {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int genre_id;
	private String genre_name;

	public Genre() {
		super();
	}

	public Genre(int genre_id, String genre_name) {
		super();
		this.genre_id = genre_id;
		this.genre_name = genre_name;
	}
	
	public Genre(String genre_name) {
		super();
		this.genre_name = genre_name;
	}


	public int getGenre_id() {
		return genre_id;
	}

	public void setGenre_id(int genre_id) {
		this.genre_id = genre_id;
	}

	public String getGenre_name() {
		return genre_name;
	}

	public void setGenre_name(String genre_name) {
		this.genre_name = genre_name;
	}
}
