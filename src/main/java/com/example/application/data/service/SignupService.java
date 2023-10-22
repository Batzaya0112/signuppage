package com.example.application.data.service;

import com.example.application.data.model.User;
import com.example.application.data.repository.UserRepository;
import com.example.application.views.SignUpView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class SignupService {
    Logger logger = Logger.getLogger(SignUpView.class.getName());
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private UserRepository userRepository;

    public User saveSignUp(User signup){

        if(signup == null){
            System.err.println("SignUp is null.");
        }else {
            String hashedPassword = passwordEncoder.encode(signup.getPassword());
            System.out.println("Plain Password: " + hashedPassword);
            signup.setPassword(hashedPassword);
            return userRepository.save(signup);
        }
        return null;
    }

}
