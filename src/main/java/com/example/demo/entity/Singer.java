package com.example.demo.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Singer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int singer_id;
	private String singer_name;
	private String singer_sex;
	private Timestamp singer_birthday;

	public Singer() {
		super();
	}

	public Singer(int singer_id, String singer_name, String singer_sex, Timestamp singer_birthday) {
		super();
		this.singer_id = singer_id;
		this.singer_name = singer_name;
		this.singer_sex = singer_sex;
		this.singer_birthday = singer_birthday;
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

}
