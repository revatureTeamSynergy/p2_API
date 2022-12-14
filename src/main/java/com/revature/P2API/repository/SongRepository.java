package com.revature.P2API.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//import com.revature.P2API.repository.models.Song;

import com.revature.P2API.models.Song;

@Repository
public interface SongRepository extends JpaRepository<Song, Long>{

	Optional<Song> findByIdTrack(String id);
	
}