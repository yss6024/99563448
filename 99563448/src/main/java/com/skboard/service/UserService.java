package com.skboard.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.skboard.dto.userDto;
import com.skboard.repository.UserRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {


    private final UserRepo memberRepository;
    private final PasswordEncoder passwordEncoder;

    public String addUser(userDto requestDto) {
        return memberRepository.save(requestDto.toEntity(passwordEncoder)).getUsername();
    }

}
