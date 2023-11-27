package com.example.application.data.service;

import com.example.application.data.model.ImageEntity;
import com.example.application.data.repository.ImageEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileService {
    @Autowired
    private  final ImageEntityRepository imageEntityRepository;

    public FileService(ImageEntityRepository imageEntityRepository) {
        this.imageEntityRepository = imageEntityRepository;
    }
    public ImageEntity saveFile(ImageEntity img){
        return imageEntityRepository.save(img);
    }
}
