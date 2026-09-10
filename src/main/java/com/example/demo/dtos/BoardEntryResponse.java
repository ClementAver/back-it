package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardEntryResponse {
    private Integer id;
    private String caption;
    private Integer position;
    private Integer board_id;
    private Integer image_id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
