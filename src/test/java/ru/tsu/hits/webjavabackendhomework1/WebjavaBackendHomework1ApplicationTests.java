package ru.tsu.hits.webjavabackendhomework1;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.tsu.hits.webjavabackendhomework1.dto.auth.LoginDto;
import ru.tsu.hits.webjavabackendhomework1.dto.auth.RegisterNewUserDto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class WebjavaBackendHomework1ApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Получение роли обычным пользователем")
    @SneakyThrows
    void getRoleByUser() {
        var data = objectMapper.writeValueAsString(new LoginDto("login", "password"));

        var jsonResp = mockMvc.perform(post("/auth/login").content(data).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals("USER", jsonResp);
    }

    @Test
    @DisplayName("Получение роли админом")
    @SneakyThrows
    void getRoleByAdmin() {
        var data = objectMapper.writeValueAsString(new LoginDto("admin", "password"));

        var jsonResp = mockMvc.perform(post("/auth/login").content(data).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals("ADMIN", jsonResp);
    }

    @Test
    @DisplayName("Получение роли пользователем с неверным логином")
    @SneakyThrows
    void getRoleByUserWithWrongUsername() {
        var data = objectMapper.writeValueAsString(new LoginDto("someWrongString", "password"));

        mockMvc.perform(post("/auth/login").content(data).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Получение роли пользователем с неверным паролем")
    @SneakyThrows
    void getRoleByUserWithWrongPassword() {
        var data = objectMapper.writeValueAsString(new LoginDto("login", "SomeWrongString"));

        mockMvc.perform(post("/auth/login").content(data).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Успешная регистрация")
    @SneakyThrows
    void regularRegistration() {
        var username = UUID.randomUUID().toString();
        var data = objectMapper.writeValueAsString(new RegisterNewUserDto("name", username, "password"));

        mockMvc.perform(post("/auth/register").content(data).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dateOfCreation").isString())
                .andExpect(jsonPath("$.dateOfEdit").isString())
                .andExpect(jsonPath("$.role").value("USER"))
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.login").value(username));
    }

    @Test
    @DisplayName("Успешная регистрация с пустым именем")
    @SneakyThrows
    void registrationWithEmptyName() {
        var username =  UUID.randomUUID().toString();
        var data = objectMapper.writeValueAsString(new RegisterNewUserDto(null, username, "password"));

        mockMvc.perform(post("/auth/register").content(data).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dateOfCreation").isString())
                .andExpect(jsonPath("$.dateOfEdit").isString())
                .andExpect(jsonPath("$.role").value("USER"))
                .andExpect(jsonPath("$.name").isEmpty())
                .andExpect(jsonPath("$.login").value(username));
    }

    @Test
    @DisplayName("Безуспешная регистрация с пустым логином")
    @SneakyThrows
    void registrationWithEmptyUsername() {
        var data = objectMapper.writeValueAsString(new RegisterNewUserDto("name", null, "password"));

        mockMvc.perform(post("/auth/register").content(data).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Безуспешная регистрация с пустым паролем")
    @SneakyThrows
    void registrationWithEmptyPassword() {
        var data = objectMapper.writeValueAsString(new RegisterNewUserDto("name", "username", ""));

        mockMvc.perform(post("/auth/register").content(data).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Безуспешная регистрация с занятым логином")
    @SneakyThrows
    void registrationWithOccupiedUsername(){
        var username = UUID.randomUUID().toString();
        var data = objectMapper.writeValueAsString(new RegisterNewUserDto("name", username, "password"));
        mockMvc.perform(post("/auth/register").content(data).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        data = objectMapper.writeValueAsString(new RegisterNewUserDto("other name", username, "otherPassword"));
        mockMvc.perform(post("/auth/register").content(data).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
