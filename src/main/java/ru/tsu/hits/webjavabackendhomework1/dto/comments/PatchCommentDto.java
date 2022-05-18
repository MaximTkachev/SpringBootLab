package ru.tsu.hits.webjavabackendhomework1.dto.comments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PatchCommentDto {
    private String authorId;
    private String text;
}
