package ru.tsu.hits.webjavabackendhomework1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tsu.hits.webjavabackendhomework1.entity.ProjectEntity;

public interface ProjectRepository extends JpaRepository<ProjectEntity, String> {
}
