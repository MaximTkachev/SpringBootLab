package ru.tsu.hits.webjavabackendhomework1.mappers;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.tsu.hits.webjavabackendhomework1.dto.projects.CreateProjectDto;
import ru.tsu.hits.webjavabackendhomework1.dto.projects.ProjectDto;
import ru.tsu.hits.webjavabackendhomework1.entity.ProjectEntity;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.UUID;

@Component
public class ProjectMapper {
    private final ModelMapper mapper;

    @Autowired
    public ProjectMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(CreateProjectDto.class, ProjectEntity.class)
                .addMappings(m -> m.skip(ProjectEntity::setId)).setPostConverter(createProjectDtoProjectEntityConverter());
    }

    public ProjectDto EntityToDto(ProjectEntity entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, ProjectDto.class);
    }

    public ProjectEntity CreateDtoToEntity(CreateProjectDto dto) {
        return Objects.isNull(dto) ? null : mapper.map(dto, ProjectEntity.class);
    }

    public Converter<CreateProjectDto, ProjectEntity> createProjectDtoProjectEntityConverter() {
        return context -> {
            CreateProjectDto source = context.getSource();
            ProjectEntity destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private void mapSpecificFields(CreateProjectDto source, ProjectEntity destination) {
        destination.setId(UUID.randomUUID().toString());
    }
}
