package com.example.demo.services;

import com.example.demo.dtos.BoardRequest;
import com.example.demo.dtos.BoardResponse;
import com.example.demo.exceptions.AlreadyExistException;
import com.example.demo.exceptions.FormatNotSupportedException;
import com.example.demo.exceptions.NotFoundException;

import java.io.IOException;
import java.util.List;

public interface BoardInterface {
    BoardResponse createBoard(BoardRequest boardRequest);
    BoardResponse readBoard(Integer id) throws NotFoundException;
    BoardResponse updateBoard(Integer id, BoardRequest boardRequest) throws NotFoundException;
    Integer deleteBoard(Integer id) throws NotFoundException;
    List<BoardResponse> readBoards();
}
