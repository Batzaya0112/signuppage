package com.example.application.security;

import com.example.application.data.model.User;
import com.example.application.data.repository.UserRepository;
import com.example.application.views.SignUpView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class SecurityService implements UserDetailsService {
    Logger logger = Logger.getLogger(SignUpView.class.getName());

    @Autowired
    private UserRepository userRepository;

    public SecurityService(UserRepository userRepository){this.userRepository = userRepository;}
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }else{
            //return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), getAuthorities(user));
            return   org.springframework.security.core.userdetails.User.withUsername(user.getUserName())
                    .password(user.getPassword())
                    .roles(String.valueOf(getAuthorities(user)))
                    .build();
        }
    }
    private static List<GrantedAuthority> getAuthorities(User user) {
        return user.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }


}