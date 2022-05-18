package ru.tsu.hits.webjavabackendhomework1.service;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import ru.tsu.hits.webjavabackendhomework1.csv.*;
import ru.tsu.hits.webjavabackendhomework1.entity.CommentEntity;
import ru.tsu.hits.webjavabackendhomework1.entity.ProjectEntity;
import ru.tsu.hits.webjavabackendhomework1.entity.TaskEntity;
import ru.tsu.hits.webjavabackendhomework1.exceprion.NotFoundException;
import ru.tsu.hits.webjavabackendhomework1.repository.CommentRepository;
import ru.tsu.hits.webjavabackendhomework1.repository.ProjectRepository;
import ru.tsu.hits.webjavabackendhomework1.repository.TaskRepository;
import ru.tsu.hits.webjavabackendhomework1.repository.UserRepository;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.Integer.parseInt;

@Service
@RequiredArgsConstructor
public class CsvService {
    private final ProjectRepository projectRepository;
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Value("${path_to_csv_file}")
    private List<String> path;

    public void getCsvFromFile(String params)  {
        List<String> filters = new ArrayList<>();
        String sort = "";
        var args = params.split(" ");
        for(var i = 0; i < args.length; i += 2){
            if (args[i].equals("-f")){
                filters.add(args[i + 1]);
            }
            if (args[i].equals("-s")){
                sort = args[i + 1];
            }
        }

        var csvStream = getClass()
                .getClassLoader().getResourceAsStream("tasksOld.csv");
        var tasks = new CsvToBeanBuilder<TaskCsv>(new InputStreamReader(Objects.requireNonNull(csvStream)))
                .withSeparator(',')
                .withType(TaskCsv.class)
                .withSkipLines(1)
                .build()
                .parse();

        var filteredTasks  = tasks.stream();
        for (var filter : filters){
            var temp = filter.split("=");
            switch (temp[0]){
                case "type":
                    filteredTasks = filteredTasks.filter(taskCsv -> taskCsv.getType().contains(temp[1]));
                    break;
                case "name":
                    filteredTasks = filteredTasks.filter(taskCsv -> taskCsv.getName().contains(temp[1]));
                    break;
                case "author":
                    filteredTasks = filteredTasks.filter(taskCsv -> taskCsv.getAuthor().contains(temp[1]));
                    break;
                case "executor":
                    filteredTasks = filteredTasks.filter(taskCsv -> taskCsv.getExecutor().contains(temp[1]));
                    break;
                case "priority":
                    filteredTasks = filteredTasks.filter(taskCsv -> taskCsv.getPriority().contains(temp[1]));
                    break;
                case "date":
                    filteredTasks = filteredTasks.filter(taskCsv -> taskCsv.getDateOfCreation().toString().contains(temp[1]));
                    break;
            }
        }

        if(!sort.equals("")){
            var temp = sort.split("=");
            switch (temp[0]){
                case "type":
                    if (temp[1].equals("desc"))
                        filteredTasks = filteredTasks.sorted(Comparator.comparing(TaskCsv::getType).reversed());
                    else
                        filteredTasks = filteredTasks.sorted(Comparator.comparing(TaskCsv::getType));
                    break;
                case "name":
                    if (temp[1].equals("desc"))
                        filteredTasks = filteredTasks.sorted(Comparator.comparing(TaskCsv::getName).reversed());
                    else
                        filteredTasks = filteredTasks.sorted(Comparator.comparing(TaskCsv::getName));
                    break;
                case "author":
                    if (temp[1].equals("desc"))
                        filteredTasks = filteredTasks.sorted(Comparator.comparing(TaskCsv::getAuthor).reversed());
                    else
                        filteredTasks = filteredTasks.sorted(Comparator.comparing(TaskCsv::getAuthor));
                    break;
                case "executor":
                    if (temp[1].equals("desc"))
                        filteredTasks = filteredTasks.sorted(Comparator.comparing(TaskCsv::getExecutor).reversed());
                    else
                        filteredTasks = filteredTasks.sorted(Comparator.comparing(TaskCsv::getExecutor));
                    break;
                case "priority":
                    if(temp[1].equals("desc"))
                        filteredTasks = filteredTasks.sorted(Comparator.comparing(TaskCsv::getPriority).reversed());
                    else
                        filteredTasks = filteredTasks.sorted(Comparator.comparing(TaskCsv::getPriority));
                    break;
                case "date":
                    if (temp[1].equals("desc"))
                        filteredTasks = filteredTasks.sorted(Comparator.comparing(TaskCsv::getDateOfCreation).reversed());
                    else
                        filteredTasks = filteredTasks.sorted(Comparator.comparing(TaskCsv::getDateOfCreation));
                    break;
            }
        }

        var result = filteredTasks.toArray(TaskCsv[]::new);
        var stringBuilder = new StringBuilder();
        for (var task : result){
            var temp = "Тип задачи: " + task.getType() + "\n" +
                    "Название задачи: " + task.getName() + "\n" +
                    "Автор: " + task.getAuthor() + "\n" +
                    "Исполнитель: " + task.getExecutor() + "\n" +
                    "Приоритет: " + task.getPriority() + "\n" +
                    "Дата создания: " + task.getDateOfCreation() + "\n\n";
            stringBuilder.append(temp);
        }

        File file = new File("Example.txt");
        try {
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(stringBuilder.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void InsertIntoDBFromFiles(){
        createTasksFromFile();
        createProjectsFromFile();
        createCommentsFromFile();
    }

    public void createProjectsFromFile() {
        var csvStream = getClass()
                .getClassLoader().getResourceAsStream("projects.csv");
        var projects = new CsvToBeanBuilder<ProjectCsv>(new InputStreamReader(Objects.requireNonNull(csvStream)))
                .withSeparator(',')
                .withType(ProjectCsv.class)
                .withSkipLines(1)
                .build()
                .parse();

        for (var project : projects) {
            var entity = new ProjectEntity(
                    UUID.randomUUID().toString(),
                    project.getDateOfCreate(),
                    project.getDateOfEdit(),
                    project.getTitle(),
                    project.getDescription(),
                    new ArrayList<>()
            );

            projectRepository.save(entity);
        }
    }

    private void createCommentsFromFile(){
        var csvStream = getClass()
                .getClassLoader().getResourceAsStream("comments.csv");
        var projects = new CsvToBeanBuilder<CommentCsv>(new InputStreamReader(Objects.requireNonNull(csvStream)))
                .withSeparator(',')
                .withType(CommentCsv.class)
                .withSkipLines(1)
                .build()
                .parse();


        for (var project : projects){
            var tasks = project.getTasks().split(":");
            var resultTasks = new ArrayList<TaskEntity>();
            for(var taskId : tasks){
                System.out.println(taskId);
                var temp = taskRepository.findById(taskId).
                        orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));

                resultTasks.add(temp);
            }
            var author = userRepository.findById(project.getAuthorId())
                    .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));

            var entity = new CommentEntity(
                    UUID.randomUUID().toString(),
                    project.getDateOfCreate(),
                    project.getDateOfEdit(),
                    author,
                    resultTasks,
                    project.getText()
            );

            commentRepository.save(entity);
        }
    }

    private void createTasksFromFile(){
        var csvStream = getClass()
                .getClassLoader().getResourceAsStream("tasks.csv");
        var tasks = new CsvToBeanBuilder<TaskEntityCsv>(new InputStreamReader(Objects.requireNonNull(csvStream)))
                .withSeparator(',')
                .withType(TaskEntityCsv.class)
                .withSkipLines(1)
                .build()
                .parse();

        for(var task : tasks) {
            var creator = userRepository.findById(task.getIdOfCreator())
                    .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
            var executor = userRepository.findById(task.getIdOfExecutor())
                    .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
            var project = projectRepository.findById(task.getIdOfProject())
                    .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));

            var taskEntity = new TaskEntity(
                    UUID.randomUUID().toString(),
                    task.getDateOfCreation(),
                    task.getDateOfEdit(),
                    task.getTitle(),
                    task.getDescription(),
                    creator,
                    executor,
                    task.getPriority(),
                    project,
                    task.getTime()
            );

            taskRepository.save(taskEntity);
        }
    }

    public void createEntitiesByTemplate() {
        for (var temp: path) {
            switch (temp) {
                case "comments":
                    createCommentsFromFile();
                    break;
                case "projects":
                    createProjectsFromFile();
                    break;
                case "tasks":
                    createTasksFromFile();
                    break;
                case "users":
                    userService.createUsersFromFile();
                    break;
                default:
                    throw new NotFoundException("Файл не найден");
            }
        }
    }
}
