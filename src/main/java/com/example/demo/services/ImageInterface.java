package com.example.demo.services;

import com.example.demo.exceptions.FormatNotSupportedException;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

public interface ImageInterface {
    String uploadImage(MultipartFile file) throws IOException, FormatNotSupportedException;
    byte[] getImage(String name) throws FileNotFoundException;
}
