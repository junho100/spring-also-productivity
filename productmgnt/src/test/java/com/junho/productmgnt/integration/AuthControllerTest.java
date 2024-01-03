package com.junho.productmgnt.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.junho.productmgnt.ProductmgntApplication;
import com.junho.productmgnt.domains.auth.model.request.SignUpRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.ResultActions;

@ContextConfiguration(classes = ProductmgntApplication.class)
public class AuthControllerTest extends BaseIntegrationTest{
    private String USERNAME = "test-username";
    private String PASSWORD = "password123!";
    private String EMAIL = "test@test.co.kr";

    @Test
    public void signUpSuccess() throws Exception {
        SignUpRequest signUpRequest = SignUpRequest.builder()
            .username(USERNAME)
            .email(EMAIL)
            .password(PASSWORD)
            .build();

        ResultActions resultActions = mvc.perform(post("/auth/sign-up")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(signUpRequest))
            .accept(MediaType.APPLICATION_JSON));

        resultActions
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.result.userId").exists());
    }

    @Test
    public void signUpEmailBadRequest() throws Exception {
        String invalidEmail = "test.test.com";
        SignUpRequest signUpRequest = SignUpRequest.builder()
            .username(USERNAME)
            .email(invalidEmail)
            .password(PASSWORD)
            .build();

        ResultActions resultActions = mvc.perform(post("/auth/sign-up")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(signUpRequest))
            .accept(MediaType.APPLICATION_JSON));

        resultActions
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").exists());
    }

    @Test
    public void signUpUserExistsBadRequest() throws Exception {
        SignUpRequest signUpRequest = SignUpRequest.builder()
            .username(USERNAME)
            .email(EMAIL)
            .password(PASSWORD)
            .build();
        mvc.perform(post("/auth/sign-up")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(signUpRequest))
            .accept(MediaType.APPLICATION_JSON));

        ResultActions resultActions = mvc.perform(post("/auth/sign-up")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(signUpRequest))
            .accept(MediaType.APPLICATION_JSON));

        resultActions
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").exists());
    }
}
