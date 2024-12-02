package com.skboard.dto;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.skboard.domain.Member;
import com.skboard.domain.Role;

import lombok.Builder;
import lombok.Getter;

@Getter
public class userDto {


    private String username;

    private String password;

    @Builder
    public userDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Member toEntity(PasswordEncoder passwordEncoder){
        return Member.builder()
                .userName(username)
                .userPassword(passwordEncoder.encode(password))
                .role(Role.USER)
                .build();
    }
}
