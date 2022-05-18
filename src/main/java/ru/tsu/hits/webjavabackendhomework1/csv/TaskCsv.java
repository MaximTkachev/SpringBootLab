package ru.tsu.hits.webjavabackendhomework1.csv;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class TaskCsv {

    @CsvBindByPosition(position = 0)
    private String type;

    @CsvBindByPosition(position = 1)
    private String name;

    @CsvBindByPosition(position = 2)
    private String author;

    @CsvBindByPosition(position = 3)
    private String executor;

    @CsvBindByPosition(position = 4)
    private String priority;

    @CsvDate(value = "dd.MM.yyyy")
    @CsvBindByPosition(position = 5)
    private Date dateOfCreation;
}
