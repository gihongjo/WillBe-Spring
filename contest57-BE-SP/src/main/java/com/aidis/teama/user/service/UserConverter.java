package com.aidis.teama.user.service;

import com.aidis.teama.user.db.UserEntity;
import com.aidis.teama.user.model.UserDTO;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserConverter {

    public UserDTO userToDTO(UserEntity userEntity) {


        return UserDTO.builder()
                .userName(userEntity.getUserName())
                .email(userEntity.getEmail())
                .build();
    }
}