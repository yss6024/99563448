package com.skboard.repository;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.skboard.domain.Member;


@EnableJpaRepositories
public interface UserRepo extends JpaRepository<Member, Long> {


    Optional<Member> findByUsername(String userId);
    
}