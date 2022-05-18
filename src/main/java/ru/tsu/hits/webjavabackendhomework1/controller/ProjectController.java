package ru.tsu.hits.webjavabackendhomework1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.webjavabackendhomework1.dto.projects.CreateProjectDto;
import ru.tsu.hits.webjavabackendhomework1.dto.projects.PatchProjectDto;
import ru.tsu.hits.webjavabackendhomework1.dto.projects.ProjectDto;
import ru.tsu.hits.webjavabackendhomework1.service.ProjectService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;

    @PreAuthorize("hasAuthority('baseUserPrm')")
    @PostMapping
    public ProjectDto createProject(@Validated @RequestBody CreateProjectDto createProjectDto){
        return projectService.createProject(createProjectDto);
    }

    @PreAuthorize("hasAuthority('baseUserPrm')")
    @GetMapping ("/{id}")
    public ProjectDto getProject(@PathVariable UUID id) {
        return projectService.getProjectById(id.toString());
    }

    @PreAuthorize("hasAuthority('baseUserPrm')")
    @PutMapping("/{id}")
    public ProjectDto editProject(@RequestBody PatchProjectDto patchProjectDto, @PathVariable UUID id){
        return projectService.editProject(patchProjectDto, id.toString());
    }

    @PreAuthorize("hasAuthority('baseUserPrm')")
    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable UUID id) {
        projectService.deleteProject(id.toString());
    }
}
