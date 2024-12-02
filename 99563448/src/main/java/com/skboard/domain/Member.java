package com.skboard.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

//@ToString(exclude = "userPassword")
@Getter
@NoArgsConstructor
@Entity
public class Member {
	
	@Id
	@Column(name="user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;
	
	@Column(name="user_email", unique = true)
	private String userEmail;
	
	@Column(name="user_password")
	private String password;
	
	@Column(name="user_name")
	private String username;
	
	/*
	@ElementCollection
	@CollectionTable(name="roles",joinColumns=@JoinColumn(name="user_id"))
	@Column(name="role")
	private List<String> Role = new ArrayList<>();*/
	
	
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	private Role role;
	
	
	@Builder
	public Member(int userId, String userEmail, String userPassword, String userName, /*List<String> Role*/ Role role) {
		this.userId=userId;
		this.userEmail=userEmail;
		this.password=userPassword;
		this.username=userName;
		//this.Role = Role;
		this.role=role;
	}

    public String getRoleKey(){
        return this.role.getKey();
    }
}