package ru.tsu.hits.webjavabackendhomework1.dto.projects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
    private String id;
    private Date dateOfCreation;
    private Date dateOfEdit;
    private String title;
    private String description;
}
