package com.revature.P2API.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.P2API.repository.LoginRepository;

import com.revature.P2API.models.User;


@Service
public class LoginService {
	
	private LoginRepository loginRepository;
	
	@Autowired
	public LoginService(LoginRepository loginRepository) {
		this.loginRepository = loginRepository;
	}

	public User login(User user) {
		
		Optional<User> login = loginRepository.findByUsername(user.getUsername());
		
		if(login.isPresent()) {
//			JwtHandler.createJwt(user.getUsername(), "login", "Music", 800000);
			return login.get();
		}else {
			System.out.println("user not found");
			return null;
					}
	}
}
