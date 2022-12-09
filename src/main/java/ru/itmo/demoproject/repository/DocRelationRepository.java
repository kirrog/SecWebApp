package ru.itmo.demoproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.demoproject.model.entity.DocRelation;

import java.util.UUID;

@Repository
public interface DocRelationRepository extends JpaRepository<DocRelation, UUID> {

}
