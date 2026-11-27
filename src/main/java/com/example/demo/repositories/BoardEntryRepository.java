package com.example.demo.repositories;

import com.example.demo.entities.BoardEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardEntryRepository extends JpaRepository<BoardEntry, Integer> {
    List<BoardEntry> findAllByBoardId_Id(Integer id);
    void deleteAllByBoardId_Id(Integer id);
    @Query("SELECT new BoardEntry(" +
            "be.id, " +
            "be.caption, " +
            "be.position, " +
            "be.board, " +
            "be.image, " +
            "be.createdAt, " +
            "be.updatedAt)" +
            "FROM BoardEntry be " +
            "WHERE be.board.id = :boardId " +
            "AND be.id != :id " +
            "ORDER BY be.position ASC, be.updatedAt DESC"
    )
    List<BoardEntry> findOthersByBoardId_Id(@Param("boardId") Integer boardId, @Param("id") Integer id);
    @Query("SELECT new BoardEntry(" +
            "be.id, " +
            "be.caption, " +
            "be.position, " +
            "be.board, " +
            "be.image, " +
            "be.createdAt, " +
            "be.updatedAt)" +
            "FROM BoardEntry be " +
            "WHERE be.board.id = :boardId " +
            "AND be.id != :id " +
            "AND be.position >= :position " +
            "ORDER BY be.position ASC, be.updatedAt DESC"
    )
    List<BoardEntry> findAllNextByBoardId_Id(@Param("id") Integer id, @Param("boardId") Integer boardId, @Param("position") Integer position);

}