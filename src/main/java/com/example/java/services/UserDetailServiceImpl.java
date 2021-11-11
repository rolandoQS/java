package com.example.java.services;


import com.example.java.entities.UserEntity;
import com.example.java.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service(value = "userService")
public class UserDetailServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;


	public UserDetailServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		List<UserEntity> userList = userRepository.findByEmail(username);
		if (userList == null || userList.isEmpty()) {
			throw new UsernameNotFoundException(username);
		}

        UserEntity user = userList.get(0);

		List<GrantedAuthority> authorities = Arrays.asList(user.getRol()).stream()
				.map(role -> new SimpleGrantedAuthority(role.get(0))).collect(Collectors.toList());
		return new User(user.getEmail(), user.getPassword(), authorities);
	}

	public List<UserEntity> findByEmail(String email) {
		return this.userRepository.findByEmail(email);
	}
}
