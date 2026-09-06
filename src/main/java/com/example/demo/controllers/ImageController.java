package com.example.demo.controllers;

import com.example.demo.dtos.BoardEntryRequest;
import com.example.demo.dtos.BoardEntryResponse;
import com.example.demo.dtos.ImageRequest;
import com.example.demo.exceptions.FormatNotSupportedException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.services.ImageService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/image")
    public Integer createImage(@RequestParam("name")MultipartFile file) throws IOException, FormatNotSupportedException {
        return imageService.createImage(file);
    }

    @GetMapping(path = {"/image/{id}"})
    public ResponseEntity<byte[]> readImage(@PathVariable("id") Integer id) throws NotFoundException {
        HttpHeaders headers = new HttpHeaders();
        byte[] imageData = imageService.readImage(id);
        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }

    @PutMapping(value = "/image/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Integer updateBoardEntry(@PathVariable @Min(value = 1, message = "L'identifiant doit être égal ou supérieur à un (1).") int id, @Valid @ModelAttribute ImageRequest imageRequest) throws NotFoundException {
        return imageService.updateImage(id, imageRequest);
    }

    @DeleteMapping("/image")
    public Integer deleteImage(Integer id) throws NotFoundException {
        return imageService.deleteImage(id);
    }

    @GetMapping(path = {"/image"}, params = {"name"})
    public ResponseEntity<byte[]> readImageByName(@PathParam("name") String name) throws NotFoundException {
        HttpHeaders headers = new HttpHeaders();

        byte[] imageData = imageService.readImageByName(name);
        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }
}
