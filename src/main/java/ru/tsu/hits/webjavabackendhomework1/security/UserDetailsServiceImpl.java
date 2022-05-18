package ru.tsu.hits.webjavabackendhomework1.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import ru.tsu.hits.webjavabackendhomework1.entity.Role;
import ru.tsu.hits.webjavabackendhomework1.exceprion.UnauthorizedException;
import ru.tsu.hits.webjavabackendhomework1.repository.UserRepository;

@Service("userDetailsServiceImpl")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User doesn't exist"));

        return SecurityUser.fromUser(user);
    }

    public Role loadUserRoleByUsername(String username, String password) throws UsernameNotFoundException {
        var user = userRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User doesn't exist"));
        if (!BCrypt.checkpw(password, user.getPassword()))
            throw new UnauthorizedException("Логин и/или пароль неверный");
        return user.getRole();
    }
}
