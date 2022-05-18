package ru.tsu.hits.webjavabackendhomework1.dto.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tsu.hits.webjavabackendhomework1.entity.Role;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatchUserDto {
    private String name;
    private String login;
    private String password;
    private Role role;
}
