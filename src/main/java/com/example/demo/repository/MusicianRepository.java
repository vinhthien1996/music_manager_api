package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Musician;

@Repository
public interface MusicianRepository extends JpaRepository<Musician, Integer>{
	
	@Query(value = "SELECT * FROM musician g WHERE LOWER(g.musician_name)=LOWER(?1)", nativeQuery = true)
	Musician findMusicianByName(String musician_name);
	
	@Query(value = "SELECT COUNT(musician_id) FROM musician", nativeQuery = true)
	int countMusician();

}
