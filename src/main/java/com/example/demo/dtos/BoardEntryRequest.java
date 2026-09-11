package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardEntryRequest {
    private String caption;
    private Integer position;
    private Integer boardId;
    private Integer imageId;
}
