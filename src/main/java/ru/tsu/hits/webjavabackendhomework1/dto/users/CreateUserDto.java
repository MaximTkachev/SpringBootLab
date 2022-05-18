package ru.tsu.hits.webjavabackendhomework1.dto.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.tsu.hits.webjavabackendhomework1.entity.Role;

import java.util.Date;

@Data
@AllArgsConstructor
public class CreateUserDto {
    private Date dateOfCreation;
    private Date dateOfEdit;
    private String name;
    private String login;
    private String password;
    private Role role;
}
