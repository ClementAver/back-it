package com.example.demo.controllers;

import com.example.demo.dtos.ImageRequest;
import com.example.demo.dtos.ImageResponse;
import com.example.demo.exceptions.FormatNotSupportedException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.services.ImageService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/image")
    public ImageResponse createImage(MultipartFile file, String alternateText) throws IOException, FormatNotSupportedException {
        return imageService.createImage(file, alternateText);
    }

    @GetMapping(path = {"/image/{id}"})
    public ImageResponse readImage(
            @PathVariable Integer id,
            @RequestParam(name = "bytes", defaultValue = "false") boolean bytes) throws NotFoundException {
        return imageService.readImage(id, bytes);
    }

    @PutMapping(value = "/image/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ImageResponse updateImage(@PathVariable @Min(value = 1, message = "L'identifiant doit être égal ou supérieur à un (1).") Integer id, @Valid @ModelAttribute ImageRequest imageRequest) throws NotFoundException {
        return imageService.updateImage(id, imageRequest);
    }

    @DeleteMapping("/image/{id}")
    public Integer deleteImage(@PathVariable @Min(value = 1, message = "L'identifiant doit être égal ou supérieur à un (1).") Integer id) throws NotFoundException {
        return id;
        // return imageService.deleteImage(id);
    }

    @GetMapping(path = {"/image"}, params = {"name"})
    public ImageResponse readImageByName(
            @RequestParam("name") String name,
            @RequestParam(name = "bytes", defaultValue = "false") boolean bytes) throws NotFoundException {
        return imageService.readImageByName(name, bytes);
    }

    @GetMapping(path = {"/image/bytes/{id}"})
    public ResponseEntity<byte[]> readImageBytes(@PathVariable Integer id) throws NotFoundException {
        byte[] imageData = imageService.readImageBytes(id);
        return new ResponseEntity<>(imageData, HttpStatus.OK);
    }

    @GetMapping("/image")
    public Map<String, Object> readImages(
            @RequestParam(name = "bytes", defaultValue = "false") boolean bytes,
            @RequestParam(name = "start", defaultValue = "0") int start,
            @RequestParam(name = "size", defaultValue = "9") int size
    ) {
        if (start % size != 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "start must be a multiple of size");
        }
        List<ImageResponse> imageList = imageService.readImages(bytes, start, size);
        long total = imageService.countImages();
        Map<String, Object> response = new HashMap<>();
        response.put("images", imageList);
        response.put("total", total);
        response.put("start", start);
        response.put("size", size);
        return response;
    }
}
