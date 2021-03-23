package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Singer;

@Repository
public interface SingerRepository extends JpaRepository<Singer, Integer> {

	@Query(value = "SELECT * FROM singer g WHERE LOWER(g.singer_name)=LOWER(?1)", nativeQuery = true)
	Singer findSingerByName(String singer_name);

	@Query(value = "SELECT COUNT(singer_id) FROM singer", nativeQuery = true)
	int countSinger();

}
