package ru.tsu.hits.webjavabackendhomework1.mappers;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.tsu.hits.webjavabackendhomework1.dto.comments.CommentDto;
import ru.tsu.hits.webjavabackendhomework1.dto.comments.CreateCommentDto;
import ru.tsu.hits.webjavabackendhomework1.entity.CommentEntity;
import ru.tsu.hits.webjavabackendhomework1.entity.TaskEntity;
import ru.tsu.hits.webjavabackendhomework1.exceprion.NotFoundException;
import ru.tsu.hits.webjavabackendhomework1.repository.TaskRepository;
import ru.tsu.hits.webjavabackendhomework1.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class CommentMapper {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public CommentMapper(ModelMapper modelMapper, UserRepository userRepository, TaskRepository taskRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(CreateCommentDto.class, CommentEntity.class)
                .addMappings(m -> m.skip(CommentEntity::setId));
        modelMapper.getTypeMap(CreateCommentDto.class, CommentEntity.class)
                .addMappings(m -> m.skip(CommentEntity::setAuthor));
        modelMapper.getTypeMap(CreateCommentDto.class, CommentEntity.class)
                .addMappings(m -> m.skip(CommentEntity::setTask)).setPostConverter(createCommentDtoCommentEntityConverter());

        modelMapper.createTypeMap(CommentEntity.class, CommentDto.class)
                .addMappings(m -> m.skip(CommentDto::setAuthorName));
        modelMapper.getTypeMap(CommentEntity.class, CommentDto.class)
                .addMappings(m -> m.skip(CommentDto::setTaskId)).setPostConverter(commentEntityCommentDtoConverter());
    }

    public CommentDto EntityToDto(CommentEntity entity) {
        return Objects.isNull(entity) ? null : modelMapper.map(entity, CommentDto.class);
    }

    public CommentEntity CreateDtoToEntity(CreateCommentDto createCommentDto) {
        return Objects.isNull(createCommentDto) ? null : modelMapper.map(createCommentDto, CommentEntity.class);
    }

    public Converter<CreateCommentDto, CommentEntity> createCommentDtoCommentEntityConverter() {
        return context -> {
            CreateCommentDto source = context.getSource();
            CommentEntity destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    public Converter<CommentEntity, CommentDto> commentEntityCommentDtoConverter() {
        return context -> {
            CommentEntity source = context.getSource();
            CommentDto destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private void mapSpecificFields(CreateCommentDto source, CommentEntity destination) {
        destination.setId(UUID.randomUUID().toString());

        if (Objects.nonNull(source.getAuthorId())) {
           destination.setAuthor(userRepository.findById(source.getAuthorId())
                   .orElseThrow(() -> new NotFoundException("Автор комментария не найден")));
        }

        if (Objects.nonNull(source.getTaskId())) {
            var tasks = new ArrayList<TaskEntity>();
            for (var taskId: source.getTaskId()) {
                tasks.add(taskRepository.findById(taskId)
                        .orElseThrow(() -> new NotFoundException("Задача не найдена")));
            }

            destination.setTask(tasks);
        }
    }

    private void mapSpecificFields(CommentEntity source, CommentDto destination) {
        if (Objects.nonNull(source.getAuthor())) {
            destination.setAuthorName(source.getAuthor().getName());
        }

        if (Objects.nonNull(source.getTask())) {
            destination.setTaskId (
                    source.getTask()
                            .stream()
                            .map(TaskEntity::getId)
                            .collect(Collectors.toList())
            );
        }
    }
}
