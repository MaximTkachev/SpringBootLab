package ru.tsu.hits.webjavabackendhomework1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import ru.tsu.hits.webjavabackendhomework1.dto.projects.CreateProjectDto;
import ru.tsu.hits.webjavabackendhomework1.dto.projects.PatchProjectDto;
import ru.tsu.hits.webjavabackendhomework1.dto.projects.ProjectDto;
import ru.tsu.hits.webjavabackendhomework1.exceprion.NotFoundException;
import ru.tsu.hits.webjavabackendhomework1.mappers.ProjectMapper;
import ru.tsu.hits.webjavabackendhomework1.repository.ProjectRepository;

import java.util.Date;


@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Transactional
    public ProjectDto createProject(CreateProjectDto createProjectDto){
        var entity = projectMapper.CreateDtoToEntity(createProjectDto);
        var savedEntity = projectRepository.save(entity);
        return projectMapper.EntityToDto(savedEntity);
    }

    @Transactional(readOnly = true)
    public ProjectDto getProjectById(String id){
        var entity = projectRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        System.out.println(entity.getId());
        return projectMapper.EntityToDto(entity);
    }

    @Transactional
    public ProjectDto editProject(PatchProjectDto patchProjectDto, String id) {
        var project = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Проект не найден"));

        project.setDateOfEdit(new Date());

        project.setTitle(patchProjectDto.getTitle());
        project.setDescription(patchProjectDto.getDescription());

        return projectMapper.EntityToDto(project);
    }

    @Transactional
    public void deleteProject(String id) {
        projectRepository.deleteById(id);
    }
}
