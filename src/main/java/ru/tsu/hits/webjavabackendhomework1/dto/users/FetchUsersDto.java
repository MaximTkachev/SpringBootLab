package ru.tsu.hits.webjavabackendhomework1.dto.users;

import lombok.Data;

import java.util.Map;

@Data
public class FetchUsersDto {
    private Map<String, String> fields;
}
