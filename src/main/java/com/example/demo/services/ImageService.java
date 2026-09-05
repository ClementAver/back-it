package com.example.demo.services;

import com.example.demo.entities.Image;
import com.example.demo.exceptions.FormatNotSupportedException;
import com.example.demo.repositories.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
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
    public String uploadImage(MultipartFile file) throws IOException, FormatNotSupportedException {
        String imageFileName = file.getOriginalFilename();
        assert imageFileName != null;
        String extension = imageFileName.substring(imageFileName.lastIndexOf(".") + 1);
        if (!extension.equals("jpg") && !extension.equals("jpeg") && !extension.equals("png")) {
            throw new FormatNotSupportedException("Format invalide (acceptés :\".jpeg\", \".jpg\" ou \".png\").");
        }
        Optional<Image> imageOptional = imageRepository.findByName(imageFileName);
        if (imageOptional.isEmpty()) {
            imageRepository.save(Image.builder()
                    .name(imageFileName)
                    .type(file.getContentType())
                    .bytes(file.getBytes())
                    .build());
        } else {
            throw new FileAlreadyExistsException("Une image porte déjà ce nom.");
        }
        return imageFileName;
    }

    @Override
    public byte[] getImage(String name) throws FileNotFoundException {
        Optional<Image> imageInDB = imageRepository.findByName(name);
        byte[] imageBytes;
        if (imageInDB.isPresent()) {
            imageBytes = imageInDB.get().getBytes();
        } else {
            throw new FileNotFoundException("Image non référencée : " + name);
        }
        return imageBytes;
    }
}
