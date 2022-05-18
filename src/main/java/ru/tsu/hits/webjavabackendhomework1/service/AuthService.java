package ru.tsu.hits.webjavabackendhomework1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import ru.tsu.hits.webjavabackendhomework1.converter.UserDtoConverter;
import ru.tsu.hits.webjavabackendhomework1.dto.auth.LoginDto;
import ru.tsu.hits.webjavabackendhomework1.dto.auth.RegisterNewUserDto;
import ru.tsu.hits.webjavabackendhomework1.dto.users.UserDto;
import ru.tsu.hits.webjavabackendhomework1.entity.UserEntity;
import ru.tsu.hits.webjavabackendhomework1.exceprion.BadRequestException;
import ru.tsu.hits.webjavabackendhomework1.mappers.UserMapper;
import ru.tsu.hits.webjavabackendhomework1.repository.UserRepository;
import ru.tsu.hits.webjavabackendhomework1.security.UserDetailsServiceImpl;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public String login(LoginDto loginDto) {
        var role= userDetailsService.loadUserRoleByUsername(loginDto.getUsername(), loginDto.getPassword());
        return role.toString();
    }

    @Transactional
    public UserDto register(RegisterNewUserDto registerNewUserDto) {
        var user = new UserEntity(registerNewUserDto.getName(), registerNewUserDto.getUsername(), passwordEncoder.encode(registerNewUserDto.getPassword()));
        var userWithTheSameUsername = userRepository.findByLogin(registerNewUserDto.getUsername());
        if (userWithTheSameUsername.isPresent())
            throw new BadRequestException("Пользователь с таким логином уже существует");

        var savedEntity =  userRepository.save(user);
        return userMapper.EntityToDto(savedEntity);
    }
}
