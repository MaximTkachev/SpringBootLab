package ru.tsu.hits.webjavabackendhomework1.dto.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tsu.hits.webjavabackendhomework1.entity.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeRoleDto {
    private Role role;
}
