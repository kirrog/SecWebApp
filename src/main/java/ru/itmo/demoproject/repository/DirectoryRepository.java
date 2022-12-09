package ru.itmo.demoproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.demoproject.model.entity.DirectoryEntity;
import ru.itmo.demoproject.model.entity.UserEntity;

import java.util.UUID;

@Repository
public interface DirectoryRepository extends JpaRepository<DirectoryEntity, UUID> {
    DirectoryEntity findDirectoryEntityByOwner(UserEntity owner);
}
