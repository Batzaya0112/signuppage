package com.example.application.data.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

@Entity
@Table(name="image_entity")
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable=false)
    @NotEmpty
    private String fileName;

    @Column(nullable=false)
    @NotEmpty
    private String uploadDirectory;

    @Column(name = "upload_date", nullable = false)
    private LocalDateTime uploadDate;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setFileName(String fileName){this.fileName = fileName;}
    public String getFileName(){return this.fileName;}
    public void setUploadDirectory(String uploadDirectory){this.uploadDirectory = uploadDirectory;}
    public String getUploadDirectory(){return this.uploadDirectory;}
    public void setUploadDate(LocalDateTime uploadDate){this.uploadDate = uploadDate;}
    public LocalDateTime getUploadDate(){return this.uploadDate;}
}
