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
public class ImageResponse {
    private Integer id;
    private String name;
    private byte[] bytes;
    private String type;
    private String alternateText;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
