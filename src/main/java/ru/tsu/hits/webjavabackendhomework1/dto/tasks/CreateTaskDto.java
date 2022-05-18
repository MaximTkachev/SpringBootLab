package ru.tsu.hits.webjavabackendhomework1.dto.tasks;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Time;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTaskDto {

    private Date dateOfCreation;

    private Date dateOfEdit;

    @NotBlank(message = "title cannot be empty")
    private String title;

    private String description;

    private String idOfCreator;

    private String idOfExecutor;

    private String priority;

    private String idOfProject;

    private Time time;
}
