package com.example.demo.vo;

import java.sql.Timestamp;

public class SongVO {

	private int song_id;
	private String song_name;
	private Timestamp release_time;
	private boolean favorite;

	public SongVO() {
		super();
	}

	public SongVO(int song_id, String song_name, Timestamp release_time, boolean favorite) {
		super();
		this.song_id = song_id;
		this.song_name = song_name;
		this.release_time = release_time;
		this.favorite = favorite;
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

	public boolean isFavorite() {
		return favorite;
	}

	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}

}
