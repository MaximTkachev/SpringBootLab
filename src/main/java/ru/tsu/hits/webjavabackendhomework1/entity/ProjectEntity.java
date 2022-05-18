package ru.tsu.hits.webjavabackendhomework1.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "projects")
public class ProjectEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "date_of_create")
    @Temporal(TemporalType.DATE)
    private Date dateOfCreation;

    @Column(name = "date_of_edit")
    @Temporal(TemporalType.DATE)
    private Date dateOfEdit;

    @Column
    private String title;

    @Column
    private String description;

    @OneToMany(mappedBy = "projectId")
    private List<TaskEntity> tasks;
}
