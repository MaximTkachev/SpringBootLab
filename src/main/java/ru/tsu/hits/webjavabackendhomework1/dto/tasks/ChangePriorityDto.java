package ru.tsu.hits.webjavabackendhomework1.dto.tasks;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePriorityDto {
    private String priority;
}
