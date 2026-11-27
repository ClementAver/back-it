package com.example.demo.services;

import com.example.demo.dtos.BoardRequest;
import com.example.demo.dtos.BoardResponse;
import com.example.demo.entities.Board;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.mappers.BoardDTOMapper;
import com.example.demo.repositories.BoardEntryRepository;
import com.example.demo.repositories.BoardRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService implements BoardInterface{

    private final BoardRepository boardRepository;
    private final BoardEntryRepository boardEntryRepository;
    private final BoardDTOMapper boardDTOMapper;

    public BoardService(BoardRepository boardRepository, BoardEntryRepository boardEntryRepository, BoardDTOMapper boardDTOMapper) {
        this.boardRepository = boardRepository;
        this.boardEntryRepository = boardEntryRepository;
        this.boardDTOMapper = boardDTOMapper;
    }

    @Override
    public BoardResponse createBoard(BoardRequest boardRequest) {
        Board board = new Board();
        board.setTitle(boardRequest.getTitle());
        board.setPosition(boardRequest.getPosition());
        boardRepository.save(board);
        return new BoardResponse(
                board.getId(),
                board.getTitle(),
                board.getPosition(),
                board.getCreatedAt(),
                board.getUpdatedAt()
        );
    }

    @Override
    public BoardResponse readBoard(Integer id) throws NotFoundException {
        Optional<Board> boardInDB = boardRepository.findById(id);
        if(boardInDB.isPresent()) {
            Board board = boardInDB.get();
            return new BoardResponse(
                    board.getId(),
                    board.getTitle(),
                    board.getPosition(),
                    board.getCreatedAt(),
                    board.getUpdatedAt()
            );
        } else {
            throw new NotFoundException("Tableau non référencé.");
        }
    }

    @Override
    public BoardResponse updateBoard(Integer id, BoardRequest boardRequest) throws NotFoundException {
        Optional<Board> boardInDB = boardRepository.findById(id);
        if (boardInDB.isPresent()) {
            Board board = boardInDB.get();
            if (boardRequest.getTitle() != null) {
                board.setTitle(boardRequest.getTitle());
            }
            if (boardRequest.getPosition() != null) {
                board.setPosition(boardRequest.getPosition());
            }
            boardRepository.save(board);
            return new BoardResponse(
                    board.getId(),
                    board.getTitle(),
                    board.getPosition(),
                    board.getCreatedAt(),
                    board.getUpdatedAt()
            );
        } else {
            throw new NotFoundException("Tableau non référencé.");
        }
    }

    @Override
    @Transactional
    public Integer deleteBoard(Integer id) throws NotFoundException {
        Optional<Board> boardInDB = boardRepository.findById(id);
        if (boardInDB.isPresent()) {
            boardEntryRepository.deleteAllByBoardId_Id(id);
            boardRepository.deleteById(id);
            return id;
        } else {
            throw new NotFoundException("Tableau non référencé.");
        }
    }

    @Override
    public List<BoardResponse> readBoards() {
        return boardRepository.findAll().stream().map(boardDTOMapper).toList();
    }
}
