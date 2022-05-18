package ru.tsu.hits.webjavabackendhomework1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import ru.tsu.hits.webjavabackendhomework1.csv.UserCsv;
import ru.tsu.hits.webjavabackendhomework1.dto.users.*;
import ru.tsu.hits.webjavabackendhomework1.entity.Role;
import ru.tsu.hits.webjavabackendhomework1.entity.UserEntity;
import ru.tsu.hits.webjavabackendhomework1.mappers.UserMapper;
import ru.tsu.hits.webjavabackendhomework1.repository.UserRepository;
import com.opencsv.bean.CsvToBeanBuilder;

import javax.persistence.criteria.Predicate;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public UserDto createUser(CreateUserDto createUserDto){
        var entity = userMapper.createUserDtoToEntity(createUserDto);
        var savedEntity = userRepository.save(entity);
        return userMapper.EntityToDto(savedEntity);
    }

    @Transactional(readOnly = true)
    public UserDto getUser(String id) {
        var entity = getUserEntity(id);

        return userMapper.EntityToDto(entity);
    }

    @Transactional
    public void createUsersFromFile() {
        var csvStream = getClass()
                .getClassLoader().getResourceAsStream("users.csv");
        var users = new CsvToBeanBuilder<UserCsv>(new InputStreamReader(Objects.requireNonNull(csvStream)))
                .withSeparator(',')
                .withType(UserCsv.class)
                .withSkipLines(1)
                .build()
                .parse();

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        for(var userFromCsv : users){
            var temp = new CreateUserDto(
                    userFromCsv.getDateOfCreate(),
                    userFromCsv.getDateOfEdit(),
                    userFromCsv.getName(),
                    userFromCsv.getLogin(),
                    userFromCsv.getPassword(),
                    userFromCsv.getRole()
            );

            createUser(temp);
        }
    }

    @Transactional(readOnly = true)
    public List<UserDto> fetchUsers(FetchUsersDto dto) {
        return userRepository
                .findAll(((root, query, cb) -> {
                    var predicates = new ArrayList<>();
                    dto.getFields().forEach((fieldName, fieldValue) ->{
                        switch (fieldName) {
                            case "dateOfCreation":
                            case "dateOfEdit":
                                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                                Date date = null;
                                try {
                                    date = formatter.parse(fieldValue);
                                } catch (ParseException e) {
                                    System.out.println(e.getMessage());
                                }
                                predicates.add(cb.equal(root.get(fieldName), date));
                                break;
                            case "name":
                            case "email":
                            case "login":
                                predicates.add(cb.like(root.get(fieldName), '%' + fieldValue + '%'));
                                break;
                            case "role":
                                predicates.add(cb.equal(root.get(fieldName), Role.valueOf(fieldValue)));
                                break;
                            default:
                                throw new RuntimeException("Неверное имя поля: " + fieldName);
                        }
                    });

                    return cb.and(predicates.toArray(new Predicate[0]));
                }))
                .stream()
                .map(userMapper::EntityToDto)
                .collect(Collectors.toList());
    }

    private UserEntity getUserEntity(String id) {
         return userRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public UserDto changeRole(String userId, ChangeRoleDto changeRoleDto) {
        var user = getUserEntity(userId);
        user.setRole(changeRoleDto.getRole());
        user.setDateOfEdit(new Date());
        return userMapper.EntityToDto(user);
    }

    @Transactional
    public UserDto editUser(PatchUserDto patchUserDto, String id) {
        var user = getUserEntity(id);

        user.setDateOfEdit(new Date());

        user.setName(patchUserDto.getName());

        user.setLogin(patchUserDto.getLogin());

        var newPassword = passwordEncoder.encode(patchUserDto.getPassword());
        user.setPassword(newPassword);

        user.setRole(patchUserDto.getRole());

        return userMapper.EntityToDto(user);
    }

    @Transactional
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
