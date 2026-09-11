package com.example.demo.controllers;

import com.example.demo.dtos.BoardEntryRequest;
import com.example.demo.dtos.BoardEntryResponse;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.services.BoardEntryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BoardEntryController {

    private final BoardEntryService boardEntryService;

    public BoardEntryController(BoardEntryService boardEntryService) {
        this.boardEntryService = boardEntryService;
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = "/board_entry", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BoardEntryResponse createBoardEntry(@Valid @ModelAttribute BoardEntryRequest boardEntryRequest) throws NotFoundException {
        return boardEntryService.createBoardEntry(boardEntryRequest);
    }

    @GetMapping("/board_entry/{id}")
    public BoardEntryResponse readBoardEntry(@PathVariable @Min(value = 1, message = "L'identifiant doit être égal ou supérieur à un (1).") Integer id) throws NotFoundException {
        return boardEntryService.readBoardEntry(id);
    }

    @PutMapping(value = "/board_entry/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BoardEntryResponse updateBoardEntry(@PathVariable @Min(value = 1, message = "L'identifiant doit être égal ou supérieur à un (1).") Integer id, @Valid @ModelAttribute BoardEntryRequest boardEntryRequest) throws NotFoundException {
        return boardEntryService.updateBoardEntry(id, boardEntryRequest);
    }

    @DeleteMapping("/board_entry/{id}")
    public Integer deleteBoardEntry(@PathVariable @Min(value = 1, message = "L'identifiant doit être égal ou supérieur à un (1).") Integer id) throws NotFoundException {
        return boardEntryService.deleteBoardEntry(id);
    }

    @GetMapping("/board_entries")
    public Map<String, List<BoardEntryResponse>> readBoardEntries(@PathParam("board_id") @Min(value = 1, message = "L'identifiant doit être égal ou supérieur à un (1).") Integer boardId) {
        List<BoardEntryResponse> boardEntryList = boardEntryService.readBoardEntries(boardId);
        Map<String, List<BoardEntryResponse>> response = new HashMap<>();
        response.put("board_entries", boardEntryList);
        return response;
    }
}
