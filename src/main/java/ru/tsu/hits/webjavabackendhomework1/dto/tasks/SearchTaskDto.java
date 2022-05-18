package ru.tsu.hits.webjavabackendhomework1.dto.tasks;

import lombok.Data;

import java.util.Date;

@Data
public class SearchTaskDto {
    private Date dateOfCreate;
    private Date dateOfEdit;
    private String description;
    private String priority;
    private String title;
    private String creatorId;
    private String executorId;

}
