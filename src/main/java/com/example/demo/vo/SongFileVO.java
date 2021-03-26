package com.example.demo.vo;

import java.sql.Timestamp;

import org.springframework.web.multipart.MultipartFile;

public class SongFileVO {

	private int song_id;
	private String song_name;
	private Timestamp release_time;
	private String genre_name;
	private String musician_name;
	private String singer_name;
	private boolean favorite;
	private MultipartFile file;

	public SongFileVO() {
		super();
	}

	public SongFileVO(int song_id, String song_name, Timestamp release_time, String genre_name, String musician_name,
			String singer_name, boolean favorite, MultipartFile file) {
		super();
		this.song_id = song_id;
		this.song_name = song_name;
		this.release_time = release_time;
		this.genre_name = genre_name;
		this.musician_name = musician_name;
		this.singer_name = singer_name;
		this.favorite = favorite;
		this.file = file;
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

	public String getGenre_name() {
		return genre_name;
	}

	public void setGenre_name(String genre_name) {
		this.genre_name = genre_name;
	}

	public String getMusician_name() {
		return musician_name;
	}

	public void setMusician_name(String musician_name) {
		this.musician_name = musician_name;
	}

	public String getSinger_name() {
		return singer_name;
	}

	public void setSinger_name(String singer_name) {
		this.singer_name = singer_name;
	}

	public boolean isFavorite() {
		return favorite;
	}

	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

}
