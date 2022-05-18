package ru.tsu.hits.webjavabackendhomework1.mappers;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.tsu.hits.webjavabackendhomework1.dto.users.CreateUserDto;
import ru.tsu.hits.webjavabackendhomework1.dto.users.UserDto;
import ru.tsu.hits.webjavabackendhomework1.entity.UserEntity;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.UUID;

@Component
public class UserMapper {
    private final ModelMapper mapper;

    @Autowired
    public UserMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(UserEntity.class, UserDto.class);
                //.addMappings(m -> m.skip(UserDto::setPassword));

        mapper.createTypeMap(CreateUserDto.class, UserEntity.class)
                .addMappings(m -> m.skip(UserEntity::setPassword));
        mapper.getTypeMap(CreateUserDto.class, UserEntity.class)
                .addMappings(m -> m.skip(UserEntity::setId)).setPostConverter(createUserDtoUserEntityConverter());
    }

    public UserDto EntityToDto(UserEntity entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, UserDto.class);
    }

    public UserEntity createUserDtoToEntity(CreateUserDto dto) {
        return Objects.isNull(dto) ? null : mapper.map(dto, UserEntity.class);
    }

    public Converter<CreateUserDto, UserEntity> createUserDtoUserEntityConverter() {
        return context -> {
            CreateUserDto source = context.getSource();
            UserEntity destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private void mapSpecificFields(CreateUserDto source, UserEntity destination) {
        destination.setId(UUID.randomUUID().toString());
        destination.setPassword(passwordEncoder.encode(source.getPassword()));
    }
}
