package com.example.demo.controllers;

import com.example.demo.dtos.ImageRequest;
import com.example.demo.dtos.ImageResponse;
import com.example.demo.exceptions.FormatNotSupportedException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.services.ImageService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/image")
    public Integer createImage(MultipartFile file, String alternateText) throws IOException, FormatNotSupportedException {
        return imageService.createImage(file, alternateText);
    }

    @GetMapping(path = {"/image/{id}"})
    public ImageResponse readImage(@PathVariable Integer id) throws NotFoundException {
        return imageService.readImage(id);
    }

    @PutMapping(value = "/image/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Integer updateImage(@PathVariable @Min(value = 1, message = "L'identifiant doit être égal ou supérieur à un (1).") Integer id, @Valid @ModelAttribute ImageRequest imageRequest) throws NotFoundException {
        return imageService.updateImage(id, imageRequest);
    }

    @DeleteMapping("/image/{id}")
    public Integer deleteImage(@PathVariable @Min(value = 1, message = "L'identifiant doit être égal ou supérieur à un (1).") Integer id) throws NotFoundException {
        return imageService.deleteImage(id);
    }

    @GetMapping(path = {"/image"}, params = {"name"})
    public ImageResponse readImageByName(@PathParam("name") String name) throws NotFoundException {
        return imageService.readImageByName(name);
    }

    @GetMapping(path = {"/image/bytes/{id}"})
    public ResponseEntity<byte[]> readImageBytes(@PathVariable Integer id) throws NotFoundException {
        byte[] imageData = imageService.readImageBytes(id);
        return new ResponseEntity<>(imageData, HttpStatus.OK);
    }
}
