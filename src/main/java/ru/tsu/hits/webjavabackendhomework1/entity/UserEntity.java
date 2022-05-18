package ru.tsu.hits.webjavabackendhomework1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity {
    public UserEntity (String name, String login, String password) {
        this.id = UUID.randomUUID().toString();
        this.dateOfCreation = new Date();
        this.dateOfEdit = new Date();
        this.name = name;
        this.login = login;
        this.password = password;
        this.role = Role.USER;
    }

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
    private String name;

    @Column
    private String login;

    @Column
    private String password;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "creatorId")
    private final List<TaskEntity> createdTasks = new ArrayList<>();

    @OneToMany(mappedBy = "executorId")
    private final List<TaskEntity> executedTasks = new ArrayList<>();

    @OneToMany(mappedBy = "author")
    private final List<CommentEntity> comments = new ArrayList<>();
}
