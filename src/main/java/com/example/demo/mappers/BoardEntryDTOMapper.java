package com.example.demo.mappers;

import com.example.demo.dtos.BoardEntryResponse;
import com.example.demo.entities.BoardEntry;
import org.springframework.stereotype.Component;
import java.util.function.Function;

@Component
public class BoardEntryDTOMapper implements Function<BoardEntry, BoardEntryResponse> {
    @Override
    public BoardEntryResponse apply(BoardEntry board_entry) {
        return new BoardEntryResponse(
                board_entry.getId(),
                board_entry.getCaption(),
                board_entry.getPosition(),
                board_entry.getBoard_id().getId(),
                board_entry.getImage_id().getId(),
                board_entry.getCreatedAt(),
                board_entry.getUpdatedAt());
    }
}
