package com.wanichnun.todoapp.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

@TestPropertySource(locations = {"/application.yml"})
public class AuthServiceTest {
    private AuthService authService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private Environment environment;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        authService = new AuthService(restTemplate, environment);
    }

    @Test
    public void test() {
        System.out.println(123);
    }
}
