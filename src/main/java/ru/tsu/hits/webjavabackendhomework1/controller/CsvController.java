package ru.tsu.hits.webjavabackendhomework1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.webjavabackendhomework1.service.CsvService;
import ru.tsu.hits.webjavabackendhomework1.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/csv")
public class CsvController {
    private final CsvService csvService;
    private final UserService userService;

    @PreAuthorize("hasAuthority('baseUserPrm')")
    @GetMapping
    public void getCsvFromFile(@RequestParam(defaultValue = "") String params){
        csvService.getCsvFromFile(params);
    }

    @PreAuthorize("hasAuthority('baseUserPrm')")
    @PostMapping("/template")
    public void selectCsvFileBtTemplate() {
        csvService.createEntitiesByTemplate();
    }

    @PreAuthorize("hasAuthority('baseUserPrm')")
    @PostMapping
    public void insertEntitiesFromFiles(){
        csvService.InsertIntoDBFromFiles();
        userService.createUsersFromFile();
    }
}
