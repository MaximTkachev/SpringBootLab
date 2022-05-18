package ru.tsu.hits.webjavabackendhomework1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.tsu.hits.webjavabackendhomework1.entity.CommentEntity;

import java.util.Collection;
import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, String> {

    @Query(value = "SELECT * FROM comments c where c.text like %:template%"
            , nativeQuery = true)
    List<CommentEntity> findCommentsByTemplate(@Param("template") String template);
}
