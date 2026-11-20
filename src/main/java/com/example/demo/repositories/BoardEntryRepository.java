package com.example.demo.repositories;

import com.example.demo.entities.BoardEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardEntryRepository extends JpaRepository<BoardEntry, Integer> {
    List<BoardEntry> findAllByBoardId_Id(Integer id);
    @Query("SELECT new BoardEntry(" +
            "be.id, " +
            "be.caption, " +
            "be.position, " +
            "be.boardId, " +
            "be.imageId, " +
            "be.createdAt, " +
            "be.updatedAt)" +
            "FROM BoardEntry be " +
            "WHERE be.boardId.id = :boardId " +
            "AND be.id != :id " +
            "ORDER BY be.position ASC, be.updatedAt DESC"
    )
    List<BoardEntry> findOthersByBoardId_Id(@Param("boardId") Integer boardId, @Param("id") Integer id);

}