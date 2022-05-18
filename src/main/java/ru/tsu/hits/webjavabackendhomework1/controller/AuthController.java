package ru.tsu.hits.webjavabackendhomework1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tsu.hits.webjavabackendhomework1.dto.auth.LoginDto;
import ru.tsu.hits.webjavabackendhomework1.dto.auth.RegisterNewUserDto;
import ru.tsu.hits.webjavabackendhomework1.dto.users.UserDto;
import ru.tsu.hits.webjavabackendhomework1.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public String login(@RequestBody LoginDto loginDto) {
        return authService.login(loginDto);
    }

    @PostMapping("/register")
    public UserDto register(@Validated @RequestBody RegisterNewUserDto dto) {
        return authService.register(dto);
    }
}
