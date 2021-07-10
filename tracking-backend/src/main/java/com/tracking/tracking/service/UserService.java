package com.tracking.tracking.service;

import com.tracking.tracking.entity.User;
import com.tracking.tracking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userService")
public class UserService implements UserDetailsService {

    @Autowired
    @Qualifier("manageUser")
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.isActive(), user.isActive(), user.isActive(), user.isActive(), buildGranted(user.getRol()));
    }

    public List<GrantedAuthority> buildGranted(byte rol) {
        String[] roles = {"USUARIO","ADMINISTRADOR"};
        List<GrantedAuthority> auths = new ArrayList<>();
        for(int i = 0; i < rol; i++) {
            auths.add(new SimpleGrantedAuthority(roles[i]));
        }
        return auths;
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User registerUser(User user) {
        return userRepository.save(user);
    }
}
