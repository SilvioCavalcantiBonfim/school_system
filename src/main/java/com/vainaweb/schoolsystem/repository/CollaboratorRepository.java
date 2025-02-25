package com.vainaweb.schoolsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vainaweb.schoolsystem.model.Collaborator;

@Repository
public interface CollaboratorRepository extends AbstractRepository, JpaRepository<Collaborator, Long> {}
