package com.example.demo.mappers;

import com.example.demo.dtos.ImageResponse;
import com.example.demo.dtos.ImageResponseWithBytes;
import com.example.demo.entities.Image;
import org.springframework.stereotype.Component;
import java.util.function.Function;

@Component
public class ImageWithBytesDTOMapper implements Function<Image, ImageResponse> {
    @Override
    public ImageResponseWithBytes apply(Image image) {
        return new ImageResponseWithBytes(
                image.getId(),
                image.getName(),
                image.getBytes(),
                image.getType(),
                image.getAlternateText(),
                image.getCreatedAt(),
                image.getUpdatedAt()
        );
    }
}
