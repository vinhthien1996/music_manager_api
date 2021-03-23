package com.example.demo.vo;

import java.sql.Timestamp;

public class SingerVO {

	private int singer_id;
	private String singer_name;
	private String singer_sex;
	private Timestamp singer_birthday;
	private int song_num;

	public SingerVO() {
		super();
	}

	public SingerVO(int singer_id, String singer_name, String singer_sex, Timestamp singer_birthday, int song_num) {
		super();
		this.singer_id = singer_id;
		this.singer_name = singer_name;
		this.singer_sex = singer_sex;
		this.singer_birthday = singer_birthday;
		this.song_num = song_num;
	}

	public int getSinger_id() {
		return singer_id;
	}

	public void setSinger_id(int singer_id) {
		this.singer_id = singer_id;
	}

	public String getSinger_name() {
		return singer_name;
	}

	public void setSinger_name(String singer_name) {
		this.singer_name = singer_name;
	}

	public String getSinger_sex() {
		return singer_sex;
	}

	public void setSinger_sex(String singer_sex) {
		this.singer_sex = singer_sex;
	}

	public Timestamp getSinger_birthday() {
		return singer_birthday;
	}

	public void setSinger_birthday(Timestamp singer_birthday) {
		this.singer_birthday = singer_birthday;
	}

	public int getSong_num() {
		return song_num;
	}

	public void setSong_num(int song_num) {
		this.song_num = song_num;
	}

}
