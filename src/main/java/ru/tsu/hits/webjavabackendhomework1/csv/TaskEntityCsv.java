package ru.tsu.hits.webjavabackendhomework1.csv;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import lombok.Data;
import lombok.ToString;

import java.sql.Time;
import java.util.Date;

@Data
@ToString
public class TaskEntityCsv {

    @CsvDate(value = "dd.MM.yyyy")
    @CsvBindByPosition(position = 0)
    private Date dateOfCreation;

    @CsvDate(value = "dd.MM.yyyy")
    @CsvBindByPosition(position = 1)
    private Date dateOfEdit;

    @CsvBindByPosition(position = 2)
    private String title;

    @CsvBindByPosition(position = 3)
    private String description;

    @CsvBindByPosition(position = 4)
    private String idOfCreator;

    @CsvBindByPosition(position = 5)
    private String idOfExecutor;

    @CsvBindByPosition(position = 6)
    private String priority;

    @CsvBindByPosition(position = 7)
    private String idOfProject;

    @CsvDate(value = "HH:MM:SS")
    @CsvBindByPosition(position = 8)
    private Date time;
}
