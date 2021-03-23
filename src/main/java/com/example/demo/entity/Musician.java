package com.example.demo.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Musician {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int musician_id;
	private String musician_name;
	private String musician_sex;
	private Timestamp musician_birthday;

	public Musician() {
		super();
	}

	public Musician(int musician_id, String musician_name, String musician_sex, Timestamp musician_birthday) {
		super();
		this.musician_id = musician_id;
		this.musician_name = musician_name;
		this.musician_sex = musician_sex;
		this.musician_birthday = musician_birthday;
	}

	public int getMusician_id() {
		return musician_id;
	}

	public void setMusician_id(int musician_id) {
		this.musician_id = musician_id;
	}

	public String getMusician_name() {
		return musician_name;
	}

	public void setMusician_name(String musician_name) {
		this.musician_name = musician_name;
	}

	public String getMusician_sex() {
		return musician_sex;
	}

	public void setMusician_sex(String musician_sex) {
		this.musician_sex = musician_sex;
	}

	public Timestamp getMusician_birthday() {
		return musician_birthday;
	}

	public void setMusician_birthday(Timestamp musician_birthday) {
		this.musician_birthday = musician_birthday;
	}

}
