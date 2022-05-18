package ru.tsu.hits.webjavabackendhomework1.csv;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import lombok.Data;
import lombok.ToString;
import ru.tsu.hits.webjavabackendhomework1.entity.Role;

import java.util.Date;

@Data
@ToString
public class UserCsv {

    @CsvDate(value = "dd.MM.yyyy")
    @CsvBindByPosition(position = 0)
    private Date dateOfCreate;

    @CsvDate(value = "dd.MM.yyyy")
    @CsvBindByPosition(position = 1)
    private Date dateOfEdit;

    @CsvBindByPosition(position = 2)
    private String login;

    @CsvBindByPosition(position = 3)
    private String name;

    @CsvBindByPosition(position = 4)
    private String password;

    @CsvBindByPosition(position = 5)
    private Role role;
}
