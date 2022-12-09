package ru.itmo.demoproject.service;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.demoproject.model.entity.DirectoryEntity;
import ru.itmo.demoproject.model.entity.DocumentEntity;
import ru.itmo.demoproject.model.entity.DocumentType;
import ru.itmo.demoproject.model.entity.UserEntity;
import ru.itmo.demoproject.model.entity.dto.ResponseBody;
import ru.itmo.demoproject.repository.DirectoryRepository;
import ru.itmo.demoproject.repository.DocumentRepository;
import ru.itmo.demoproject.repository.UserRepository;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GoogleCopyService {
    private final GoogleDriveService googleDriveService;
    private final DirectoryRepository directoryRepository;
    private final UserRepository userRepository;
    private final DocumentRepository documentEntity;

    // get drive by email or drive id? and copy file by it id to created after registration file
    // creation of relation entity
    public ResponseBody makeCopy(String email, String token, DocumentType documentType) throws IOException {
        Drive drive = googleDriveService.getDriveByEmail(email);
        File fileMetadata = new File();
        fileMetadata.setName(drive.files().get(token).execute().getName());
        UserEntity userEntity = userRepository.findUserEntityByEmail(email);
        DirectoryEntity directoryEntity = directoryRepository.findDirectoryEntityByOwner(userEntity);
        List<String> parents = List.of(directoryEntity.getToken());
        fileMetadata.setParents(parents);
        File copied = drive.files().copy(token, fileMetadata).execute();
        documentEntity.saveAndFlush(DocumentEntity.builder()
                .parent(directoryEntity)
                .documentType(documentType)
                .owner(userEntity)
                .token(copied.getId())
                .build());
        return ResponseBody.builder().build();
    }
}
