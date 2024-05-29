package com.example.application.data.service;

import com.example.application.data.model.ImageEntity;
import com.example.application.data.model.User;
import com.example.application.data.repository.ImageEntityRepository;
import com.example.application.data.repository.UserRepository;
import com.example.application.security.AuthenticatedUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class FileService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthenticatedUser authenticatedUser;
    @Autowired
    private final ImageEntityRepository imageEntityRepository;

    public FileService(ImageEntityRepository imageEntityRepository,
            UserRepository userRepository,
            AuthenticatedUser authenticatedUser) {
        this.imageEntityRepository = imageEntityRepository;
        this.authenticatedUser = authenticatedUser;
        this.userRepository = userRepository;
    }

    public void saveFileToDatabase(ImageEntity imageEntity) {
        System.out.println("saveToDatabase =====>" + imageEntity.getUploadDate().toString());
        imageEntityRepository.save(imageEntity);
    }

    public List<ImageEntity> LoggedUserFiles() {
        List<ImageEntity> userFiles = new ArrayList<>();
        User userDetails = LoginUserCheck();
        if (userDetails != null) {
            List<ImageEntity> allFiles = getAllFiles();
            for (ImageEntity file : allFiles) {
                if (userDetails.getId().equals(file.getUserId())) {
                    userFiles.add(file);
                }
            }
        } else {
            System.out.println("User details not found");
        }
        return userFiles;
    }

    public User LoginUserCheck() {
        Optional<UserDetails> u = authenticatedUser.getAutenticatedUser();

        if (u.isPresent()) {
            System.out.println("Person username: " + u.get().getUsername());
            User userInfo = userRepository.findByUsername(u.get().getUsername());
            if (userInfo != null) {
                System.out.println("Person userInfo: " + u.get().getUsername());
                return userInfo;
            } else {
                System.out.println("User not found");
                return null;
            }
        } else {
            System.out.println("Authenticated user is not present");
            return null;
        }
    }

    public List<ImageEntity> getAllFiles() {
        return imageEntityRepository.findAll();
    }
}
