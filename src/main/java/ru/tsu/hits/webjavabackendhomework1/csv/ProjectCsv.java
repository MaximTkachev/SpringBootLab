package ru.tsu.hits.webjavabackendhomework1.csv;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class ProjectCsv {
    @CsvDate(value = "dd.MM.yyyy")
    @CsvBindByPosition(position = 0)
    private Date dateOfCreate;

    @CsvDate(value = "dd.MM.yyyy")
    @CsvBindByPosition(position = 1)
    private Date dateOfEdit;

    @CsvBindByPosition(position = 2)
    private String title;

    @CsvBindByPosition(position = 3)
    private String description;
}
