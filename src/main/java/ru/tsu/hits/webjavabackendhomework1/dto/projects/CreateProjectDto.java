package ru.tsu.hits.webjavabackendhomework1.dto.projects;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class CreateProjectDto {

    private Date dateOfCreation;

    private Date dateOfEdit;

    @NotBlank
    private String title;

    @NotBlank
    @Size(min = 10)
    private String description;
}
