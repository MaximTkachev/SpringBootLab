package ru.tsu.hits.webjavabackendhomework1.dto.tasks;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private String id;
    private Date dateOfCreation;
    private Date dateOfEdit;
    private String title;
    private String description;
    private String creator_id;
    private String executor_id;
    private String priority;
    private String project_id;
    private Time time;
}
