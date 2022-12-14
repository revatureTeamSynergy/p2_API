package com.revature.P2API.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.P2API.models.MusicList;

@Repository
public interface MusicListRepository extends JpaRepository<MusicList, Long>{
	
	Optional<MusicList> findByName(String name);
	
}
