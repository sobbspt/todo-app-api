package com.wanichnun.todoapp.service;

import com.wanichnun.todoapp.model.AuthRequest;
import com.wanichnun.todoapp.model.AuthResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
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

    @Value("${line.getAccessTokenUrl}")
    private String getAccessTokenUrl;

    @Value("${line.clientId}")
    private String clientId;

    @Value("${line.clientSecret}")
    private String clientSecret;

    @Value("${line.grantType}")
    private String grantType;

    @Value("${line.redirectUri}")
    private String redirectUri;

    private RestTemplate restTemplate;

    @Autowired
    public AuthService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public AuthResponse getAccessToken(AuthRequest request) {
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
