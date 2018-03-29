package com.project.manager.repositories;


import com.project.manager.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Optional<List<Project>> findByMembers_Id(Long id);
}
