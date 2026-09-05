package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "image")
@Getter
@Setter
@Builder
@NoArgsConstructor()
@AllArgsConstructor()
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Lob
    @Column(name = "bytes", columnDefinition="mediumblob")
    private byte[] bytes;
    private String type;
    private String alternate_text;
    private String caption;
    @Column(name = "created_at")
    private LocalDate createdAt;
    @Column(name = "updated_at")
    private LocalDate updatedAt;
}
