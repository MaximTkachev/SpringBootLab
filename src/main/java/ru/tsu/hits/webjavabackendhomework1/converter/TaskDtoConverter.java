package ru.tsu.hits.webjavabackendhomework1.converter;

import ru.tsu.hits.webjavabackendhomework1.dto.tasks.CreateTaskDto;
import ru.tsu.hits.webjavabackendhomework1.dto.tasks.TaskDto;
import ru.tsu.hits.webjavabackendhomework1.entity.ProjectEntity;
import ru.tsu.hits.webjavabackendhomework1.entity.TaskEntity;
import ru.tsu.hits.webjavabackendhomework1.entity.UserEntity;

import java.sql.Time;
import java.util.ArrayList;
import java.util.UUID;

public class TaskDtoConverter {
    public static TaskEntity convertCreateDtoToEntity(CreateTaskDto taskDto, UserEntity creator, UserEntity executor, ProjectEntity project){
        return new TaskEntity(
                UUID.randomUUID().toString(),
                taskDto.getDateOfCreation(),
                taskDto.getDateOfEdit(),
                taskDto.getTitle(),
                taskDto.getDescription(),
                creator,
                executor,
                taskDto.getPriority(),
                project,
                taskDto.getTime()
        );
    }

    public static TaskDto convertEntityToDto(TaskEntity taskEntity){
        var time = new Time(taskEntity.getTime().getTime());

        return new TaskDto(
                taskEntity.getId(),
                taskEntity.getDateOfCreation(),
                taskEntity.getDateOfEdit(),
                taskEntity.getTitle(),
                taskEntity.getDescription(),
                taskEntity.getCreatorId().getId(),
                taskEntity.getExecutorId().getId(),
                taskEntity.getPriority(),
                taskEntity.getProjectId().getId(),
                time
        );
    }
}
