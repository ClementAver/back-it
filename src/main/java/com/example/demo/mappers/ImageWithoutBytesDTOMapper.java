package com.example.demo.mappers;

import com.example.demo.dtos.ImageResponse;
import com.example.demo.dtos.ImageResponseWithoutBytes;
import com.example.demo.entities.Image;
import org.springframework.stereotype.Component;
import java.util.function.Function;

@Component
public class ImageWithoutBytesDTOMapper implements Function<Image, ImageResponse> {
    @Override
    public ImageResponseWithoutBytes apply(Image image) {
        return new ImageResponseWithoutBytes(
                image.getId(),
                image.getName(),
                image.getType(),
                image.getAlternateText(),
                image.getCreatedAt(),
                image.getUpdatedAt()
        );
    }
}
