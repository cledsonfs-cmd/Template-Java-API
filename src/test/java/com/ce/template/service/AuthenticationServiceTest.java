package com.ce.template.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    AuthenticationManager mockAuthenticationManager;


    Authentication mockAuthentication;


    @BeforeEach
    void setUp() {

    }

}