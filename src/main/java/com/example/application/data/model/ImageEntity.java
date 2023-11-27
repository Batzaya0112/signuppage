package com.example.application.data.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name="image_entity")
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    @NotEmpty
    private String nameFile;

    @Column(nullable=false)
    @NotEmpty
    private String diretoryFile;

    @Column(nullable=false)
    @NotEmpty
    private String uploadDate;

    @Lob
    @Column(name="image_data", columnDefinition = "bytea")
    private byte[] imageData;

    public void setImageData(byte[] imageData){ this.imageData = imageData;}
    public byte[] getImageData(){return this.imageData;}
}
