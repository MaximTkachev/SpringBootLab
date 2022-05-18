package ru.tsu.hits.webjavabackendhomework1.service;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.TypeCache;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ReflectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.HttpClientErrorException;
import ru.tsu.hits.webjavabackendhomework1.dto.tasks.*;
import ru.tsu.hits.webjavabackendhomework1.entity.ProjectEntity;
import ru.tsu.hits.webjavabackendhomework1.entity.TaskEntity;
import ru.tsu.hits.webjavabackendhomework1.entity.UserEntity;
import ru.tsu.hits.webjavabackendhomework1.exceprion.NotFoundException;
import ru.tsu.hits.webjavabackendhomework1.mappers.TaskMapper;
import ru.tsu.hits.webjavabackendhomework1.repository.ProjectRepository;
import ru.tsu.hits.webjavabackendhomework1.repository.TaskRepository;
import ru.tsu.hits.webjavabackendhomework1.repository.UserRepository;

import javax.persistence.criteria.Predicate;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Validated
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final TaskMapper taskMapper;

    @Transactional
    public TaskDto createTask(@Valid CreateTaskDto createTaskDto) { //✔
        var entity = taskMapper.CreateDtoToEntity(createTaskDto);
        var savedEntity = taskRepository.save(entity);
        return taskMapper.EntityToDto(savedEntity);
    }

    @Transactional(readOnly = true)
    public TaskDto getTask(String id) { //✔
        var entity = getTaskEntity(id);
        return taskMapper.EntityToDto(entity);
    }

    @Transactional(readOnly = true)
    public ProjectEntity getProjectEntityById(String id){ // ❔
        return projectRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public UserEntity getUserEntityById(String id) { // ❔
        return userRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public List<TaskDto> getTaskByProject(ProjectEntity projectEntity) {
        var tasksEntities = taskRepository.findByProjectId(projectEntity);
        var tasksDto = new ArrayList<TaskDto>();
        for (TaskEntity taskEntity : tasksEntities) {
            tasksDto.add(taskMapper.EntityToDto(taskEntity));
        }

        return tasksDto;
    }

    @Transactional(readOnly = true)
    public List<TaskDto> getTaskByExecutor(UserEntity userEntity){
        var tasksEntities = taskRepository.findByExecutorId(userEntity);
        var tasksDto = new ArrayList<TaskDto>();
        for (TaskEntity taskEntity : tasksEntities){
            tasksDto.add(taskMapper.EntityToDto(taskEntity));
        }

        return tasksDto;
    }

    @Transactional(readOnly = true)
    public PageImpl<TaskDto> fetchTasks(FetchTasksDto dto, Integer pageNumber, Integer recordNumber){
        Specification<TaskEntity> specification = (root, query, cb) -> {
            var predicates = new ArrayList<>();
            dto.getFields().forEach((fieldName, fieldValue) ->{
                switch (fieldName) {
                    case "dateOfCreating":
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
                    case "creatorId":
                    case "executorId":
                    case "projectId":
                        predicates.add(cb.equal(root.get(fieldName).get("uuid"), fieldValue));
                        break;
                    case "priority":
                        predicates.add(cb.equal(root.get(fieldName), fieldValue));
                        break;
                    case "title":
                    case "description":
                        predicates.add(cb.like(root.get(fieldName), '%' + fieldValue + '%'));
                        break;
                    default:
                        throw new RuntimeException("Неверное имя поля: " + fieldName);
                }
            });

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        var orders = new ArrayList<Sort.Order>();
        if (dto.getSortFields() != null)
            for (var key: dto.getSortFields().keySet()) {
                orders.add(new Sort.Order(dto.getSortFields().get(key), key));
            }

        var pageRequest = PageRequest.of(pageNumber, recordNumber);

        if (!orders.isEmpty())
            pageRequest = PageRequest.of(pageNumber, recordNumber, Sort.by(orders));

        var entityPage = taskRepository.findAll(specification, pageRequest);

        var dtos = entityPage
                .stream()
                .map(taskMapper::EntityToDto)
                .collect(Collectors.toList());

        return new PageImpl<TaskDto>(dtos, entityPage.getPageable(), entityPage.getTotalElements());
    }

    private TaskEntity getTaskEntity(String id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Задача не найдена"));
    }

    @Transactional
    public TaskDto editTask(PatchTaskDto patchTaskDto, String id) {
        var task = getTaskEntity(id);

        task.setDateOfEdit(new Date());

        task.setTitle(patchTaskDto.getTitle());

        task.setDescription(patchTaskDto.getDescription());

        if (patchTaskDto.getIdOfExecutor() != null) {
            var user = userRepository.findById(patchTaskDto.getIdOfExecutor())
                    .orElseThrow(() -> new NotFoundException("Исполнитель задачи не найден"));
            task.setExecutorId(user);
        }
        else {
            task.setExecutorId(null);
        }

        task.setPriority(patchTaskDto.getPriority());

        if (patchTaskDto.getIdOfProject() != null) {
            var project = projectRepository.findById(patchTaskDto.getIdOfProject())
                    .orElseThrow(() -> new NotFoundException("Проект не найден"));
            task.setProjectId(project);
        }
        else
            task.setProjectId(null);

        task.setTime(patchTaskDto.getTime());

        return taskMapper.EntityToDto(task);
    }

    @Transactional
    public TaskDto editTaskByReflection(PatchTaskDto patchTaskDto, String id) {
        var task = getTaskEntity(id);

        var fields = patchTaskDto.getClass().getDeclaredFields();

        for (var field : fields) {
            try {
                field.setAccessible(true);
                if (field.get(patchTaskDto) == null)
                    continue;

                var taskField = ReflectionUtils.findField(TaskEntity.class, field.getName());
                if (taskField == null)
                    continue;

                taskField.setAccessible(true);
                ReflectionUtils.setField(taskField, task, field.get(patchTaskDto));

                taskField.setAccessible(false);
                field.setAccessible(false);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Ошибка сервера");
            }
        }

        return taskMapper.EntityToDto(task);
    }

    @Transactional
    public void deleteTask(String id) {
        taskRepository.deleteById(id);
    }

    @Transactional
    public TaskDto changePriority(ChangePriorityDto changePriorityDto, String id){
        var task = getTaskEntity(id);
        task.setPriority(changePriorityDto.getPriority());
        return taskMapper.EntityToDto(task);
    }

    @Transactional
    public TaskDto changeExecutor(ChangeExecutorDto changeExecutorDto, String id) {
        var task = getTaskEntity(id);
        var executor = userRepository.findById(changeExecutorDto.getExecutorId())
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        task.setExecutorId(executor);
        return taskMapper.EntityToDto(task);
    }
}
