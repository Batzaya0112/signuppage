package com.example.application.data.service;

import com.example.application.data.model.Signup;
import com.example.application.data.repository.SignUpRepository;
import org.springframework.stereotype.Service;

@Service
public class SignupService {

    private final SignUpRepository signUpRepository;

    public SignupService(SignUpRepository signUpRepository){
        this.signUpRepository = signUpRepository;
    }
    public void saveSignUp(Signup signup){
        if(signup == null){
            System.err.println("SignUp is null.");
            return;
        }
        System.Logger logger = System.getLogger("signup ==========> " + signup);
        System.err.println("SignUp is null." + logger);
        signUpRepository.save(signup);
    }

}
