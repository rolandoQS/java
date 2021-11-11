package com.example.java.config;

import com.example.java.entities.UserEntity;
import com.example.java.services.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InfoAdicionalToken implements TokenEnhancer {

	@Autowired
	private UserDetailServiceImpl userDetailService;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Map<String, Object> info = new HashMap<>();
		List<UserEntity> userList = userDetailService.findByEmail(authentication.getName());
        UserEntity user = userList.get(0);
		info.put("nombre", user.getName());
		info.put("rol", user.getRol());
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		return accessToken;
	}
}
