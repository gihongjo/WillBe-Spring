package com.aidis.teama.user.service;

import com.aidis.teama.user.db.GoogleUserEntity;
import com.aidis.teama.user.db.GoogleUserRepository;
import com.aidis.teama.user.model.CustomUserDetails;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {


    private final GoogleUserRepository googleUserRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        GoogleUserEntity user = googleUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return new CustomUserDetails(user); // CustomUserDetails 사용
    }


}
