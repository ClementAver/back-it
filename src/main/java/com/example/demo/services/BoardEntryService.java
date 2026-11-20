package com.example.demo.services;

import com.example.demo.dtos.BoardEntryRequest;
import com.example.demo.dtos.BoardEntryResponse;
import com.example.demo.entities.Board;
import com.example.demo.entities.BoardEntry;
import com.example.demo.entities.Image;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.mappers.BoardEntryDTOMapper;
import com.example.demo.repositories.BoardEntryRepository;
import com.example.demo.repositories.BoardRepository;
import com.example.demo.repositories.ImageRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class BoardEntryService implements BoardEntryInterface {

    private final BoardEntryRepository boardEntryRepository;
    private final BoardRepository boardRepository;
    private final ImageRepository imageRepository;
    private final BoardEntryDTOMapper boardEntryDTOMapper;

    public BoardEntryService(BoardEntryRepository boardEntryRepository, BoardRepository boardRepository, ImageRepository imageRepository, BoardEntryDTOMapper boardEntryDTOMapper) {
        this.boardEntryRepository = boardEntryRepository;
        this.boardRepository = boardRepository;
        this.imageRepository = imageRepository;
        this.boardEntryDTOMapper = boardEntryDTOMapper;
    }

    @Override
    public BoardEntryResponse createBoardEntry(BoardEntryRequest boardEntryRequest) throws NotFoundException {
        BoardEntry boardEntry = new BoardEntry();
        Optional<Board> boardInDB = boardRepository.findById(boardEntryRequest.getBoardId());
        if (boardInDB.isPresent()) {
            boardEntry.setBoardId(boardInDB.get());
        } else {
            throw new NotFoundException("Tableau non référencé.");
        }
        Optional<Image> imageInDB = imageRepository.findById(boardEntryRequest.getImageId());
        if (imageInDB.isPresent()) {
            boardEntry.setImageId(imageInDB.get());
        } else {
            throw new NotFoundException("Image non référencée.");
        }
        boardEntry.setCaption(boardEntryRequest.getCaption());
        boardEntry.setPosition(boardEntryRequest.getPosition());
        boardEntryRepository.save(boardEntry);
        return new BoardEntryResponse(
          boardEntry.getId(),
          boardEntry.getCaption(),
          boardEntry.getPosition(),
          boardEntry.getBoardId().getId(),
          boardEntry.getImageId().getId(),
          boardEntry.getCreatedAt(),
          boardEntry.getUpdatedAt()
        );
    }

    @Override
    public BoardEntryResponse readBoardEntry(Integer id) throws NotFoundException {
        Optional<BoardEntry> boardEntryInDB = boardEntryRepository.findById(id);
        if(boardEntryInDB.isPresent()) {
            BoardEntry boardEntry = boardEntryInDB.get();
            return new BoardEntryResponse(
                    boardEntry.getId(),
                    boardEntry.getCaption(),
                    boardEntry.getPosition(),
                    boardEntry.getBoardId().getId(),
                    boardEntry.getImageId().getId(),
                    boardEntry.getCreatedAt(),
                    boardEntry.getUpdatedAt()
            );
        } else {
            throw new NotFoundException("Tableau non référencé.");
        }
    }

    @Override
    public BoardEntryResponse updateBoardEntry(Integer id, BoardEntryRequest boardEntryRequest) throws NotFoundException {
        Optional<BoardEntry> boardEntryInDB = boardEntryRepository.findById(id);
        if (boardEntryInDB.isPresent()) {
            BoardEntry boardEntry = boardEntryInDB.get();

            boolean moved = false;

            if (boardEntryRequest.getCaption() != null) {
                boardEntry.setCaption(boardEntryRequest.getCaption());
            }

            if (boardEntryRequest.getPosition() != null && !boardEntry.getPosition().equals(boardEntryRequest.getPosition())) {
                moved = true;
                boardEntry.setPosition(boardEntryRequest.getPosition());
            }

            if (boardEntryRequest.getBoardId() != null && !boardEntry.getBoardId().getId().equals(boardEntryRequest.getBoardId())) {
                moved = true;
                Optional<Board> boardInDB = boardRepository.findById(boardEntryRequest.getBoardId());
                if (boardInDB.isPresent()) {
                    Board board = boardInDB.get();
                    boardEntry.setBoardId(board);
                } else {
                    throw new NotFoundException("Tableau non référencé.");
                }
            }

            if (!moved) {
                boardEntryRepository.save(boardEntry);
            } else {
                List<BoardEntry> boardEntries = boardEntryRepository.findOthersByBoardId_Id(
                        boardEntry.getBoardId().getId(),
                        boardEntry.getId()
                );
                boardEntries.add(boardEntry.getPosition() - 1, boardEntry);
                IntStream.range(0, boardEntries.size())
                        .forEach(i -> {
                            BoardEntry it = boardEntries.get(i);
                            it.setPosition(i + 1);
                            boardEntryRepository.save(it);
                        });
            }

            return new BoardEntryResponse(
                    boardEntry.getId(),
                    boardEntry.getCaption(),
                    boardEntry.getPosition(),
                    boardEntry.getBoardId().getId(),
                    boardEntry.getImageId().getId(),
                    boardEntry.getCreatedAt(),
                    boardEntry.getUpdatedAt()
            );
        } else {
            throw new NotFoundException("Entrée non référencée.");
        }
    }

    @Override
    public Integer deleteBoardEntry(Integer id) throws NotFoundException {
        Optional<BoardEntry> boardEntryInDB = boardEntryRepository.findById(id);
        if (boardEntryInDB.isPresent()) {
            boardEntryRepository.deleteById(id);
            return id;
        } else {
            throw new NotFoundException("Entrée non référencée.");
        }
    }

    @Override
    public List<BoardEntryResponse> createBoardEntries(List<BoardEntryRequest> boardEntryRequests) throws NotFoundException {
        List<BoardEntryResponse> responses = new ArrayList<>();
        for (BoardEntryRequest request : boardEntryRequests) {
            responses.add(createBoardEntry(request));
        }
        return responses;
    }

    @Override
    public List<BoardEntryResponse> readBoardEntries(Integer id) {
        return boardEntryRepository.findAllByBoardId_Id(id).stream().map(boardEntryDTOMapper).toList();
    }

    @Override
    public List<BoardEntryResponse> updateBoardEntries(List<BoardEntryRequest> boardEntryRequests) throws NotFoundException {
        List<BoardEntryResponse> responses = new ArrayList<>();
        for (BoardEntryRequest request : boardEntryRequests) {
            responses.add(updateBoardEntry(request.getId(), request));
        }
        return responses;
    }
}


