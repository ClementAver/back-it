package com.example.demo.services;

import com.example.demo.dtos.BoardEntryRequest;
import com.example.demo.dtos.BoardEntryResponse;
import com.example.demo.entities.Board;
import com.example.demo.entities.BoardEntry;
import com.example.demo.entities.Image;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.repositories.BoardEntryRepository;
import com.example.demo.repositories.BoardRepository;
import com.example.demo.repositories.ImageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class BoardEntryService implements BoardEntryInterface {

    private final BoardEntryRepository boardEntryRepository;
    private final BoardRepository boardRepository;
    private final ImageRepository imageRepository;

    public BoardEntryService(BoardEntryRepository boardEntryRepository, BoardRepository boardRepository, ImageRepository imageRepository) {
        this.boardEntryRepository = boardEntryRepository;
        this.boardRepository = boardRepository;
        this.imageRepository = imageRepository;
    }

    @Override
    public BoardEntryResponse createBoardEntry(BoardEntryRequest boardEntryRequest) throws NotFoundException {
        BoardEntry boardEntry = new BoardEntry();
        Optional<Board> boardInDB = boardRepository.findById(boardEntryRequest.getBoard_id());
        if (boardInDB.isPresent()) {
            boardEntry.setBoard_id(boardInDB.get());
        } else {
            throw new NotFoundException("Tableau non référencé.");
        }
        Optional<Image> imageInDB = imageRepository.findById(boardEntryRequest.getImage_id());
        if (imageInDB.isPresent()) {
            boardEntry.setImage_id(imageInDB.get());
        } else {
            throw new NotFoundException("Image non référencée.");
        }
        boardEntry.setOrder(boardEntryRequest.getOrder());
        boardEntry.setCreatedAt(LocalDate.now());
        boardEntry.setUpdatedAt(LocalDate.now());
        boardEntryRepository.save(boardEntry);
        return new BoardEntryResponse(
          boardEntry.getId(),
          boardEntry.getOrder(),
          boardEntry.getBoard_id().getId(),
          boardEntry.getImage_id().getId(),
          boardEntry.getCreatedAt(),
          boardEntry.getUpdatedAt()
        );
    }

    @Override
    public BoardEntryResponse updateBoardEntry(Integer id, BoardEntryRequest boardEntryRequest) throws NotFoundException {
        Optional<BoardEntry> boardEntryInDB = boardEntryRepository.findById(id);
        if (boardEntryInDB.isPresent()) {
            BoardEntry boardEntry = boardEntryInDB.get();
            if (boardEntryRequest.getOrder() != null) {
                boardEntry.setOrder(boardEntryRequest.getOrder());
            }
            boardEntry.setUpdatedAt(LocalDate.now());
            boardEntryRepository.save(boardEntry);
            return new BoardEntryResponse(
                    boardEntry.getId(),
                    boardEntry.getOrder(),
                    boardEntry.getBoard_id().getId(),
                    boardEntry.getImage_id().getId(),
                    boardEntry.getCreatedAt(),
                    boardEntry.getUpdatedAt()
            );
        } else {
            throw new NotFoundException("Entrée non référencée.");
        }
    }
}


