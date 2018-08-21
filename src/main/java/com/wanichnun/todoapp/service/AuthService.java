package com.wanichnun.todoapp.service;

import com.wanichnun.todoapp.model.AuthRequest;
import com.wanichnun.todoapp.model.AuthResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class AuthService {
    @Autowired
    private RestTemplate restTemplate;

    public AuthResponse getAccessToken(AuthRequest request) {
        String getAccessTokenUrl = "https://api.line.me/v2/oauth/accessToken";

        String clientId = "1601887173";
        String clientSecret = "9df37fe9ad6c3c6ba0d6146be02489d5";
        String grantType = "authorization_code";
        String redirectUri = "https://todo-d976c.firebaseapp.com/callback";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("code", request.getCode());
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("grant_type", grantType);
        map.add("redirect_uri", redirectUri);

        HttpEntity<MultiValueMap<String, String>> requestForm = new HttpEntity<>(map, headers);

        ResponseEntity<AuthResponse> response = null;
        try {
            response = restTemplate.postForEntity(getAccessTokenUrl, requestForm , AuthResponse.class);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }

        return response.getBody();
    }
}
