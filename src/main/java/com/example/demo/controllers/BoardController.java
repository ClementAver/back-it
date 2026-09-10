package com.example.demo.controllers;

import com.example.demo.dtos.BoardRequest;
import com.example.demo.dtos.BoardResponse;
import com.example.demo.exceptions.AlreadyExistException;
import com.example.demo.exceptions.FormatNotSupportedException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.services.BoardService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = "/board", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BoardResponse createBoard(@Valid @ModelAttribute BoardRequest boardRequest) {
        return boardService.createBoard(boardRequest);
    }

    @GetMapping("/board/{id}")
    public BoardResponse readBoard(@PathVariable @Min(value = 1, message = "L'identifiant doit être égal ou supérieur à un (1).") Integer id) throws NotFoundException {
        return boardService.readBoard(id);
    }

    @PutMapping(value = "/board/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BoardResponse updateBoard(@PathVariable @Min(value = 1, message = "L'identifiant doit être égal ou supérieur à un (1).") Integer id, @Valid @ModelAttribute BoardRequest boardRequest) throws NotFoundException {
        return boardService.updateBoard(id, boardRequest);
    }

    @DeleteMapping("/board")
    public Integer deleteBoard(@PathVariable @Min(value = 1, message = "L'identifiant doit être égal ou supérieur à un (1).") Integer id) throws NotFoundException {
        return boardService.deleteBoard(id);
    }

    @GetMapping("/boards")
    public Map<String, List<BoardResponse>> readBoards() {
        List<BoardResponse> boardList = boardService.readBoards();
        Map<String, List<BoardResponse>> response = new HashMap<>();
        response.put("boards", boardList);
        return response;
    }
}
