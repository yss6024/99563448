package com.skboard.repository;


import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.skboard.domain.Member;


//@Repository
public class UserDetail implements UserDetails{
	
	private Member user;
	
	public UserDetail(Member user) {
		this.user = user;
	}
	
	

	/*
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		ArrayList<GrantedAuthority> auth = new ArrayList<>();
        for(String role : user.getRole()){
            auth.add(new GrantedAuthority() {
                @Override
                public String getAuthority() {
                    return role;
                }
            });
        }
        return auth;
	}*/
	
    @Override
    public String getPassword() {
        return user.getUsername();
    }
    
    @Override
    public String getUsername() {
        return user.getPassword();
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }
    
    @Override
    public boolean isEnabled() {
        return true;
    }



	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}
    

}
