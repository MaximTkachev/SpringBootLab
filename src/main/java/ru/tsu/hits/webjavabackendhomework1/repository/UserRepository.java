package ru.tsu.hits.webjavabackendhomework1.repository;

import org.apache.catalina.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.tsu.hits.webjavabackendhomework1.entity.Role;
import ru.tsu.hits.webjavabackendhomework1.entity.UserEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, String>, JpaSpecificationExecutor<UserEntity> {

    @Query(value = "select * from users u where u.date_of_create = :date_of_create and " +
            "u.date_of_edit = :date_of_edit and u.login = :login " +
            "and u.name = :name and u.role = :role",
    nativeQuery = true)
    List<UserEntity> getUsersByTemplate(@Param("date_of_create")Date date_of_create,
                                        @Param("date_of_edit")Date date_of_edit,
                                        @Param("login")String login,
                                        @Param("name")String name,
                                        @Param("role")String role);

    List<UserEntity> findAll(Specification<UserEntity> specification);

    Optional<UserEntity> findByLogin(String login);

    Optional<UserEntity> findByLoginAndPassword(String login, String password);
}
