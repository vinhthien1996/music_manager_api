package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer>{
	
	@Query(value = "SELECT * FROM genre g WHERE LOWER(g.genre_name)=LOWER(?1)", nativeQuery = true)
	Genre findGenreByName(String genre_name);
	
	@Query(value = "SELECT COUNT(genre_id) FROM genre", nativeQuery = true)
	int countGenre();

}
