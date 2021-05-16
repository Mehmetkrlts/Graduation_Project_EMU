package com.graduation.project.repository;

import com.graduation.project.model.entities.Jury;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JuryRepository extends JpaRepository<Jury,Long> {
    Jury findByUsername(String usernama);
}
