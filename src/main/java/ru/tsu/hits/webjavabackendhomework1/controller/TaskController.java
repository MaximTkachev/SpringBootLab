package ru.tsu.hits.webjavabackendhomework1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.webjavabackendhomework1.dto.tasks.*;
import ru.tsu.hits.webjavabackendhomework1.service.TaskService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    @PreAuthorize("hasAuthority('baseUserPrm')")
    @PostMapping
    public TaskDto createTask(@Validated @RequestBody CreateTaskDto createTaskDto){
        return taskService.createTask(createTaskDto);
    }

    @PreAuthorize("hasAuthority('baseUserPrm')")
    @GetMapping("/{id}")
    public TaskDto getTask(@PathVariable UUID id){
        return taskService.getTask(id.toString());
    }

    @PreAuthorize("hasAuthority('baseUserPrm')")
    @GetMapping("/project/{id}")
    public List<TaskDto> getTasksByProject(@PathVariable UUID id){
        return taskService.getTaskByProject(taskService.getProjectEntityById(id.toString()));
    }

    @PreAuthorize("hasAuthority('baseUserPrm')")
    @GetMapping("/executor/{id}")
    public List<TaskDto> getTasksByExecutor(@PathVariable UUID id){
        return taskService.getTaskByExecutor(taskService.getUserEntityById(id.toString()));
    }

    @PreAuthorize("hasAuthority('baseUserPrm')")
    @GetMapping("/search")
    public PageImpl<TaskDto> getTasksByTemplates(@RequestBody FetchTasksDto fetchTasksDto,
                                                 @RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam Integer recordsNumber){
        return taskService.fetchTasks(fetchTasksDto, pageNumber, recordsNumber);
    }

    @PreAuthorize("hasAuthority('baseUserPrm')")
    @PutMapping("/{id}")
    public TaskDto editTask(@RequestBody PatchTaskDto patchTaskDto, @PathVariable UUID id){
        return taskService.editTask(patchTaskDto, id.toString());
    }

    @PreAuthorize("hasAuthority('baseUserPrm')")
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable UUID id) {
        taskService.deleteTask(id.toString());
    }

    @PreAuthorize("hasAuthority('baseUserPrm')")
    @PatchMapping("/{id}/priority")
    public TaskDto changePriority(@PathVariable UUID id, @RequestBody ChangePriorityDto changePriorityDto) {
        return taskService.changePriority(changePriorityDto, id.toString());
    }

    @PreAuthorize("hasAuthority('baseUserPrm')")
    @PatchMapping("/{id}/executor")
    public TaskDto changeExecutor(@PathVariable UUID id, @RequestBody ChangeExecutorDto changeExecutorDto) {
        return taskService.changeExecutor(changeExecutorDto, id.toString());
    }
}
