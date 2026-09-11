package com.example.demo.services;

import com.example.demo.dtos.ImageRequest;
import com.example.demo.entities.Image;
import com.example.demo.exceptions.FormatNotSupportedException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.repositories.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.Optional;

@Service
public class ImageService implements ImageInterface {

    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public Integer createImage(MultipartFile file, String alternateText) throws IOException, FormatNotSupportedException {
        String imageFileName = file.getOriginalFilename();
        assert imageFileName != null;
        String extension = imageFileName.substring(imageFileName.lastIndexOf(".") + 1);
        if (!extension.equals("jpg") && !extension.equals("jpeg") && !extension.equals("png")) {
            throw new FormatNotSupportedException("Format invalide (acceptés :\".jpeg\", \".jpg\" ou \".png\").");
        }
        Optional<Image> imageOptional = imageRepository.findByName(imageFileName);
        if (imageOptional.isEmpty()) {
            Image image = Image.builder()
                    .name(imageFileName)
                    .bytes(file.getBytes())
                    .type(file.getContentType())
                    .alternateText(alternateText)
                    .build();
            imageRepository.save(image);
            return image.getId();
        } else {
            throw new FileAlreadyExistsException("Une image porte déjà ce nom.");
        }
    }

    @Override
    public byte[] readImage(Integer id) throws NotFoundException {
        Optional<Image> imageInDB = imageRepository.findById(id);
        byte[] imageBytes;
        if (imageInDB.isPresent()) {
            imageBytes = imageInDB.get().getBytes();
        } else {
            throw new NotFoundException("Image non référencée.");
        }
        return imageBytes;
    }

    @Override
    public Integer updateImage(Integer id, ImageRequest imageRequest) throws NotFoundException {
        Optional<Image> imageInDB = imageRepository.findById(id);
        if (imageInDB.isPresent()) {
            Image image = imageInDB.get();
            if (imageRequest.getAlternateText() != null) {
                image.setAlternateText(imageRequest.getAlternateText());
            }
            imageRepository.save(image);
            return image.getId();
        } else {
            throw new NotFoundException("Image non référencée.");
        }
    }

    @Override
    public Integer deleteImage(Integer id)throws NotFoundException {
        Optional<Image> imageInDB = imageRepository.findById(id);
        if (imageInDB.isPresent()) {
            imageRepository.deleteById(id);
            return id;
        } else {
            throw new NotFoundException("Image non référencée.");
        }
    }

    @Override
    public byte[] readImageByName(String name) throws NotFoundException {
        Optional<Image> imageInDB = imageRepository.findByName(name);
        byte[] imageBytes;
        if (imageInDB.isPresent()) {
            imageBytes = imageInDB.get().getBytes();
        } else {
            throw new NotFoundException("Image non référencée : " + name);
        }
        return imageBytes;
    }
}
