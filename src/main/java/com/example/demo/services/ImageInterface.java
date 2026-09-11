package com.example.demo.services;

import com.example.demo.dtos.ImageRequest;
import com.example.demo.exceptions.FormatNotSupportedException;
import com.example.demo.exceptions.NotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageInterface {
    Integer createImage(MultipartFile file, String alternateText) throws IOException, FormatNotSupportedException;
    byte[] readImage(Integer id) throws NotFoundException;
    Integer updateImage(Integer id, ImageRequest imageRequest) throws NotFoundException;
    Integer deleteImage(Integer id) throws NotFoundException;
    byte[] readImageByName(String name) throws NotFoundException;
}
