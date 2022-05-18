package ru.tsu.hits.webjavabackendhomework1.mappers;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.tsu.hits.webjavabackendhomework1.dto.tasks.CreateTaskDto;
import ru.tsu.hits.webjavabackendhomework1.dto.tasks.TaskDto;
import ru.tsu.hits.webjavabackendhomework1.entity.TaskEntity;
import ru.tsu.hits.webjavabackendhomework1.exceprion.NotFoundException;
import ru.tsu.hits.webjavabackendhomework1.repository.ProjectRepository;
import ru.tsu.hits.webjavabackendhomework1.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.UUID;

@Component
public class TaskMapper {
    private final ModelMapper mapper;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public TaskMapper(ModelMapper mapper, UserRepository userRepository, ProjectRepository projectRepository) {
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(TaskEntity.class, TaskDto.class)
                .addMappings(m -> m.skip(TaskDto::setExecutor_id));
        mapper.getTypeMap(TaskEntity.class, TaskDto.class)
                .addMappings(m -> m.skip(TaskDto::setProject_id));
        mapper.getTypeMap(TaskEntity.class, TaskDto.class)
                .addMappings(m -> m.skip(TaskDto::setCreator_id)).setPostConverter(toDtoConverter());


        mapper.createTypeMap(CreateTaskDto.class, TaskEntity.class)
                .addMappings(m -> m.skip(TaskEntity::setProjectId));
        mapper.getTypeMap(CreateTaskDto.class, TaskEntity.class)
                .addMappings(m -> m.skip(TaskEntity::setCreatorId));
        mapper.getTypeMap(CreateTaskDto.class, TaskEntity.class)
                .addMappings(m -> m.skip(TaskEntity::setExecutorId));
        mapper.getTypeMap(CreateTaskDto.class, TaskEntity.class)
                .addMappings(m -> m.skip(TaskEntity::setId)).setPostConverter(createDtoToEntityConverter());
    }

    public TaskDto EntityToDto(TaskEntity entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, TaskDto.class);
    }

    public TaskEntity CreateDtoToEntity(CreateTaskDto createTaskDto) {
        return Objects.isNull(createTaskDto) ? null : mapper.map(createTaskDto, TaskEntity.class);
    }

    public Converter<TaskEntity, TaskDto> toDtoConverter() {
        return context -> {
            TaskEntity source = context.getSource();
            TaskDto destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    public Converter<CreateTaskDto, TaskEntity> createDtoToEntityConverter() {
        return context -> {
            CreateTaskDto source = context.getSource();
            TaskEntity destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private void mapSpecificFields(CreateTaskDto source, TaskEntity destination) {
        destination.setId(UUID.randomUUID().toString());

        if (Objects.nonNull(source.getIdOfProject())) {
            destination.setProjectId(projectRepository.findById(source.getIdOfProject())
                    .orElseThrow(() -> new NotFoundException("Проект не найден")));
        }

        if (Objects.nonNull(source.getIdOfCreator())) {
            destination.setCreatorId(userRepository.findById(source.getIdOfCreator())
                    .orElseThrow(() -> new NotFoundException("Создатель задачи не найден")));
        }

        if (Objects.nonNull(source.getIdOfExecutor())) {
            destination.setExecutorId(userRepository.findById(source.getIdOfExecutor())
                    .orElseThrow(() -> new NotFoundException("Исполнитель задачи не найден")));
        }
    }

    private void mapSpecificFields(TaskEntity source, TaskDto destination) {
        if (Objects.nonNull(source.getCreatorId()))
            destination.setCreator_id(source.getCreatorId().getId());

        if (Objects.nonNull(source.getExecutorId()))
            destination.setExecutor_id(source.getExecutorId().getId());

        if (Objects.nonNull(source.getProjectId()))
            destination.setProject_id(source.getProjectId().getId());
    }
}
