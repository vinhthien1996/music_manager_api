package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Song;

@Repository
public interface SongRepository extends JpaRepository<Song, Integer>{

	@Query(value = "SELECT * FROM song g WHERE LOWER(g.song_name)=LOWER(?1)", nativeQuery = true)
	Song findSongByName(String singer_name);

	@Query(value = "SELECT COUNT(song_id) FROM song", nativeQuery = true)
	int countSong();
	
	@Query(value = "SELECT COUNT(song_id) FROM song WHERE song.favorite = 1", nativeQuery = true)
	int countSongFavorite();
	
	@Query(value = "SELECT COUNT(song_id) FROM song WHERE song.singer_id = ?1", nativeQuery = true)
	int countSongBySinger(int id);
}
