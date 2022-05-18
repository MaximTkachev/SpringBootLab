package ru.tsu.hits.webjavabackendhomework1.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterNewUserDto {
    private String name;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;
}
