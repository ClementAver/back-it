package com.example.demo.mappers;

import com.example.demo.dtos.BoardResponse;
import com.example.demo.entities.Board;
import org.springframework.stereotype.Component;
import java.util.function.Function;

// Not really useful as we return all attributes, but could change.
@Component
public class BoardDTOMapper implements Function<Board, BoardResponse> {
    @Override
    public BoardResponse apply(Board board) {
        return new BoardResponse(
                board.getId(),
                board.getTitle(),
                board.getCreatedAt(),
                board.getUpdatedAt());
    }
}
