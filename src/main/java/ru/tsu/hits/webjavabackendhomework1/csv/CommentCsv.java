package ru.tsu.hits.webjavabackendhomework1.csv;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class CommentCsv {
    @CsvDate(value = "dd.MM.yyyy")
    @CsvBindByPosition(position = 0)
    private Date dateOfCreate;

    @CsvDate(value = "dd.MM.yyyy")
    @CsvBindByPosition(position = 1)
    private Date dateOfEdit;

    @CsvBindByPosition(position = 2)
    private String authorId;

    @CsvBindByPosition(position = 3)
    private String tasks;

    @CsvBindByPosition(position = 4)
    private String text;
}
