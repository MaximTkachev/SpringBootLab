package ru.tsu.hits.webjavabackendhomework1.converter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.tsu.hits.webjavabackendhomework1.config.SecurityConfig;
import ru.tsu.hits.webjavabackendhomework1.dto.auth.RegisterNewUserDto;
import ru.tsu.hits.webjavabackendhomework1.dto.users.CreateUserDto;
import ru.tsu.hits.webjavabackendhomework1.dto.users.UserDto;
import ru.tsu.hits.webjavabackendhomework1.entity.Role;
import ru.tsu.hits.webjavabackendhomework1.entity.UserEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class UserDtoConverter {
    public static UserDto ConvertEntityToDto(UserEntity userEntity){
        return new UserDto(
                userEntity.getId(),
                userEntity.getDateOfCreation(),
                userEntity.getDateOfEdit(),
                userEntity.getName(),
                userEntity.getLogin(),
                userEntity.getRole()
        );
    }

    public static UserEntity ConvertCreateDtoToEntity(CreateUserDto createUserDto, String password) {
        return new UserEntity(
                UUID.randomUUID().toString(),
                createUserDto.getDateOfCreation(),
                createUserDto.getDateOfEdit(),
                createUserDto.getName(),
                createUserDto.getLogin(),
                password,
                createUserDto.getRole()
        );
    }
}
