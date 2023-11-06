package com.example.application.data.service;

import com.example.application.data.model.User;
import com.example.application.data.repository.UserRepository;
import com.example.application.views.SignUpView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UserService {
    Logger logger = Logger.getLogger(SignUpView.class.getName());
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveSignUp(User signup){

        if(signup == null){
            System.err.println("SignUp is null.");
            return null;
        }else {
            String hashedPassword = passwordEncoder.encode(signup.getPassword());
            signup.setPassword(hashedPassword);
            return userRepository.save(signup);
        }
    }
    public Optional<User> findUser(Long id){
        if(id == null){
            return null;
        } else {
            return userRepository.findById(id);
        }
    }

}
