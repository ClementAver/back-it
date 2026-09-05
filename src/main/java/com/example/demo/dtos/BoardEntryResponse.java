package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardEntryResponse {
    private Integer id;
    private Integer order;
    private Integer board_id;
    private Integer image_id;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
