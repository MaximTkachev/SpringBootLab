package ru.tsu.hits.webjavabackendhomework1.converter;

import ru.tsu.hits.webjavabackendhomework1.dto.projects.CreateProjectDto;
import ru.tsu.hits.webjavabackendhomework1.dto.projects.ProjectDto;
import ru.tsu.hits.webjavabackendhomework1.entity.ProjectEntity;

import java.util.ArrayList;
import java.util.UUID;

public class ProjectDtoConverter {
    public static ProjectEntity convertCreateDtoToEntity(CreateProjectDto createProjectDto)
    {
        return new ProjectEntity(
                UUID.randomUUID().toString(),
                createProjectDto.getDateOfCreation(),
                createProjectDto.getDateOfEdit(),
                createProjectDto.getTitle(),
                createProjectDto.getDescription(),
                new ArrayList<>()
        );
    }

    public static ProjectDto convertEntityToDto(ProjectEntity entity)
    {
        return new ProjectDto(
                entity.getId(),
                entity.getDateOfCreation(),
                entity.getDateOfEdit(),
                entity.getTitle(),
                entity.getDescription()
        );
    }
}
