package ru.tsu.hits.webjavabackendhomework1.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.tsu.hits.webjavabackendhomework1.entity.ProjectEntity;
import ru.tsu.hits.webjavabackendhomework1.entity.TaskEntity;
import ru.tsu.hits.webjavabackendhomework1.entity.UserEntity;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, String>, JpaSpecificationExecutor<TaskEntity> {
    @Query(value = "select * from tasks t where t.date_of_creating = :date_of_creating and " +
            "t.date_of_edit = :date_of_edit and t.description like %:description% and " +
            "t.priority = :priority and t.title like %:title% and t.creator_id = :creatorId " +
            "and t.executor_id = :executorId", nativeQuery = true)
    List<TaskEntity> findTasksByTemplates(@Param("date_of_creating") Date date_of_creating,
    @Param("date_of_edit") Date date_of_edit, @Param("description") String description,
    @Param("priority") String priority, @Param("title") String title,
    @Param("creatorId") String creatorId, @Param("executorId") String executorId);

    List<TaskEntity> findByProjectId(ProjectEntity projectEntity);

    List<TaskEntity> findByExecutorId(UserEntity userEntity);

    List<TaskEntity> findAll(Specification<TaskEntity> specification);
}
