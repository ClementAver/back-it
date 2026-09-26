package com.example.demo.services;

import com.example.demo.dtos.ImageRequest;
import com.example.demo.dtos.ImageResponse;
import com.example.demo.dtos.ImageResponseWithBytes;
import com.example.demo.dtos.ImageResponseWithoutBytes;
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
    public ImageResponse readImage(Integer id, boolean bytes) throws NotFoundException {
        if (bytes == true) {
            Optional<Image> imageInDB = imageRepository.findById(id);
            if (imageInDB.isPresent()) {
                Image image = imageInDB.get();
                return new ImageResponseWithBytes(
                        image.getId(),
                        image.getName(),
                        image.getBytes(),
                        image.getType(),
                        image.getAlternateText(),
                        image.getCreatedAt(),
                        image.getUpdatedAt()
                );
            } else {
                throw new NotFoundException("Image non référencée.");
            }
        } else {
            Optional<ImageResponseWithoutBytes> imageInDB = imageRepository.findByIdWithoutBytes(id);
            if (imageInDB.isPresent()) {
                ImageResponseWithoutBytes image = imageInDB.get();
                return new ImageResponseWithoutBytes(
                        image.getId(),
                        image.getName(),
                        image.getType(),
                        image.getAlternateText(),
                        image.getCreatedAt(),
                        image.getUpdatedAt()
                );
            } else {
                throw new NotFoundException("Image non référencée.");
            }
        }
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
    public ImageResponse readImageByName(String name,  boolean bytes) throws NotFoundException {
        if (bytes == true) {
            Optional<Image> imageInDB = imageRepository.findByName(name);
            if (imageInDB.isPresent()) {
                Image image = imageInDB.get();
                return new ImageResponseWithBytes(
                        image.getId(),
                        image.getName(),
                        image.getBytes(),
                        image.getType(),
                        image.getAlternateText(),
                        image.getCreatedAt(),
                        image.getUpdatedAt()
                );
            } else {
                throw new NotFoundException("Image non référencée.");
            }
        } else {
            Optional<ImageResponseWithoutBytes> imageInDB = imageRepository.findByNameWithoutBytes(name);
            if (imageInDB.isPresent()) {
                ImageResponseWithoutBytes image = imageInDB.get();
                return new ImageResponseWithoutBytes(
                        image.getId(),
                        image.getName(),
                        image.getType(),
                        image.getAlternateText(),
                        image.getCreatedAt(),
                        image.getUpdatedAt()
                );
            } else {
                throw new NotFoundException("Image non référencée.");
            }
        }
    }

    @Override
    public byte[] readImageBytes(Integer id) throws NotFoundException {
        Optional<Image> imageInDB = imageRepository.findById(id);
        byte[] imageBytes;
        if (imageInDB.isPresent()) {
            imageBytes = imageInDB.get().getBytes();
        } else {
            throw new NotFoundException("Image non référencée.");
        }
        return imageBytes;
    }
}
