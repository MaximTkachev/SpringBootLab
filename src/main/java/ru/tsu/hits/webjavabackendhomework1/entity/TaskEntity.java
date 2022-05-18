package ru.tsu.hits.webjavabackendhomework1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Time;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tasks")
public class TaskEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "date_of_creating")
    @Temporal(TemporalType.DATE)
    private Date dateOfCreation;

    @Column(name = "date_of_edit")
    @Temporal(TemporalType.DATE)
    private Date dateOfEdit;

    @Column
    private String title;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    private UserEntity creatorId;

    @ManyToOne
    @JoinColumn(name = "executor_id", referencedColumnName = "id")
    private UserEntity executorId;

    @Column
    private String priority;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private ProjectEntity projectId;

    @Column
    @Temporal(TemporalType.TIME)
    private Date time;
/*
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tasks_comments",
    joinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "comment_id", referencedColumnName = "id")
    )
    */
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "task")
    private final List<CommentEntity> comments = new ArrayList<>();
}
