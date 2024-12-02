package com.skboard.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.skboard.domain.Member;
import com.skboard.repository.UserDetail;
import com.skboard.repository.UserRepo;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {

    private final UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    	
        Member user = repo.findByUsername(userName).orElseThrow(() -> new UsernameNotFoundException("없는 회원 입니다..."));
        
        //return new UserDetail(user);
        
        return User.builder().username(user.getUsername()).password(user.getPassword()).roles(user.getRole().name()).build();
    }

}
