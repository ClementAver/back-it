package com.example.demo.services;

import com.example.demo.dtos.ImageRequest;
import com.example.demo.dtos.ImageResponse;
import com.example.demo.exceptions.FormatNotSupportedException;
import com.example.demo.exceptions.NotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageInterface {
    Integer createImage(MultipartFile file, String alternateText) throws IOException, FormatNotSupportedException;
    ImageResponse readImage(Integer id, boolean bytes) throws NotFoundException;
    Integer updateImage(Integer id, ImageRequest imageRequest) throws NotFoundException;
    Integer deleteImage(Integer id) throws NotFoundException;
    ImageResponse readImageByName(String name,  boolean bytes) throws NotFoundException;
    byte[] readImageBytes(Integer id) throws NotFoundException;
}
