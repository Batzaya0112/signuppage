package com.example.application.data.service;

import com.example.application.data.model.Signup;
import com.example.application.data.repository.SignUpRepository;
import com.example.application.views.SignUpView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class SignupService {
    Logger logger = Logger.getLogger(SignUpView.class.getName());
    @Autowired
    private SignUpRepository signUpRepository;

//    public SignupService(SignUpRepository signUpRepository){
//        this.signUpRepository = signUpRepository;
//    }

    public Signup saveSignUp(Signup signup){
        if(signup == null){
            System.err.println("SignUp is null.");
            logger.log(Level.INFO, "signup null =========> SignUp is null. " + signup.getFirstName());
        }else {
            logger.log(Level.INFO, "signup not null =========> SignUp is not null. " + signup.getFirstName());
            logger.log(Level.INFO, "signup not null =========> SignUp is not null. " + signup.getLastName());
            logger.log(Level.INFO, "signup not null =========> SignUp is not null. " + signup.getEmail());
        }
        return signUpRepository.save(signup);
    }

}
