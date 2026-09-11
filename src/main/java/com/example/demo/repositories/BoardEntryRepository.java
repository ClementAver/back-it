package com.example.demo.repositories;

import com.example.demo.entities.BoardEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardEntryRepository extends JpaRepository<BoardEntry, Integer> {
    List<BoardEntry> findAllByBoardId_Id(Integer id);
}
