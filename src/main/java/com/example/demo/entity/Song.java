package com.example.demo.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Song {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int song_id;
	private String song_name;
	private Timestamp release_time;

	@OneToOne
	@JoinColumn(name = "genre_id")
	private Genre genre;

	@OneToOne
	@JoinColumn(name = "musician_id")
	private Musician musician;

	@OneToOne
	@JoinColumn(name = "singer_id")
	private Singer singer;
	
	@Column(name = "favorite", columnDefinition = "boolean default false", nullable = false)
	private boolean favorite;
	private String url;

	public Song() {
		super();
	}

	public Song(int song_id, String song_name, Timestamp release_time, Genre genre, Musician musician, Singer singer,
			boolean favorite, String url) {
		super();
		this.song_id = song_id;
		this.song_name = song_name;
		this.release_time = release_time;
		this.genre = genre;
		this.musician = musician;
		this.singer = singer;
		this.favorite = favorite;
		this.url = url;
	}

	public int getSong_id() {
		return song_id;
	}

	public void setSong_id(int song_id) {
		this.song_id = song_id;
	}

	public String getSong_name() {
		return song_name;
	}

	public void setSong_name(String song_name) {
		this.song_name = song_name;
	}

	public Timestamp getRelease_time() {
		return release_time;
	}

	public void setRelease_time(Timestamp release_time) {
		this.release_time = release_time;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public Musician getMusician() {
		return musician;
	}

	public void setMusician(Musician musician) {
		this.musician = musician;
	}

	public Singer getSinger() {
		return singer;
	}

	public void setSinger(Singer singer) {
		this.singer = singer;
	}

	public boolean getFavorite() {
		return favorite;
	}

	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}	

}
