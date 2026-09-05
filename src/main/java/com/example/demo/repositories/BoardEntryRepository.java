package com.example.demo.repositories;

import com.example.demo.entities.BoardEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardEntryRepository extends JpaRepository<BoardEntry, Integer> { }
