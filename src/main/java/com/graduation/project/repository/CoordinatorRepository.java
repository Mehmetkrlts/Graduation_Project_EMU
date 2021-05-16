package com.graduation.project.repository;

import com.graduation.project.model.entities.Coordinator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoordinatorRepository extends JpaRepository<Coordinator,Long> {
    Coordinator findByUsername(String username);

}