package ru.tsu.hits.webjavabackendhomework1.dto.comments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private String id;
    private Date dateOfCreation;
    private Date dateOfEdit;
    private String authorName;
    private List<String> taskId;
    private String text;
}
