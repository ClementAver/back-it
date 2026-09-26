package com.example.demo.repositories;

import com.example.demo.dtos.ImageResponseWithoutBytes;
import com.example.demo.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image,Integer> {
    Optional<Image> findByName(String name);
    @Query("SELECT new com.example.demo.dtos.ImageResponseWithoutBytes(i.id, i.name, i.type, i.alternateText, i.createdAt, i.updatedAt) " +
            "FROM Image i WHERE i.id = :id")
    Optional<ImageResponseWithoutBytes> findByIdWithoutBytes(@Param("id") Integer id);
    @Query("SELECT new com.example.demo.dtos.ImageResponseWithoutBytes(i.id, i.name, i.type, i.alternateText, i.createdAt, i.updatedAt) " +
            "FROM Image i WHERE i.name = :name")
    Optional<ImageResponseWithoutBytes> findByNameWithoutBytes(@Param("name") String name);
}