package ru.tsu.hits.webjavabackendhomework1.dto.tasks;

import lombok.Data;
import org.apache.commons.collections4.map.LinkedMap;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.Map;

@Data
public class FetchTasksDto {
    private Map<String, String> fields;
    private LinkedMap<String, Sort.Direction> sortFields;
}
