package com.example.demo.vo;

public class GenreVO {
	private int genre_id;
	private String genre_name;
	private int song_num;

	public GenreVO() {
		super();
	}

	public GenreVO(int genre_id, String genre_name, int song_num) {
		super();
		this.genre_id = genre_id;
		this.genre_name = genre_name;
		this.song_num = song_num;
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

	public int getSong_num() {
		return song_num;
	}

	public void setSong_num(int song_num) {
		this.song_num = song_num;
	}

}
