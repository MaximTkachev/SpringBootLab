package ru.tsu.hits.webjavabackendhomework1.dto.comments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentDto {
    private Date dateOfCreation;
    private Date dateOfEdit;
    private String authorId;
    private List<String> taskId;
    @NotBlank
    private String text;
}
