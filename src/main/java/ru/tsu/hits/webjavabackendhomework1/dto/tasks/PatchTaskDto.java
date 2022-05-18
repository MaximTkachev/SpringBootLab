package ru.tsu.hits.webjavabackendhomework1.dto.tasks;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatchTaskDto {
    private String title;
    private String description;
    private String idOfCreator;
    private String idOfExecutor;
    private String priority;
    private String idOfProject;
    private Time time;
}
