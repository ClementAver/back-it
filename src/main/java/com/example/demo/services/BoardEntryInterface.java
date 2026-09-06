package com.example.demo.services;

import com.example.demo.dtos.BoardEntryRequest;
import com.example.demo.dtos.BoardEntryResponse;
import com.example.demo.exceptions.AlreadyExistException;
import com.example.demo.exceptions.NotFoundException;

public interface BoardEntryInterface {
    BoardEntryResponse createBoardEntry(BoardEntryRequest boardEntryRequest) throws AlreadyExistException, NotFoundException;
    BoardEntryResponse readBoardEntry(Integer id) throws NotFoundException;
    BoardEntryResponse updateBoardEntry(Integer id, BoardEntryRequest boardEntryRequest) throws AlreadyExistException, NotFoundException;
    Integer deleteBoardEntry(Integer id) throws NotFoundException;
}
