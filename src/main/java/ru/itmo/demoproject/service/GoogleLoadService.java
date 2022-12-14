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
import ru.itmo.demoproject.repository.DirectoryEntityRepository;
import ru.itmo.demoproject.repository.DocumentEntityRepository;
import ru.itmo.demoproject.repository.DocumentTypeRepository;
import ru.itmo.demoproject.repository.UserEntityRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GoogleLoadService {
    private final GoogleDriveService googleDriveService;
    private final UserEntityRepository userEntityRepository;
    private final DirectoryEntityRepository directoryEntityRepository;
    private final DocumentTypeRepository documentTypeRepository;
    private final DocumentEntityRepository documentEntityRepository;

    // create drive or ask to reg. get shareing link, get from it document id and create document type and document entity
    public ResponseBody loadFileToSystem(String token, String email) throws IOException {
        System.out.println("Load request: token: " + token + " email: " + email);
        Drive drive = googleDriveService.getDriveByEmail(email);
        System.out.println("Drive toString: " + drive.toString());
        UserEntity userEntity = userEntityRepository.findUserEntityByEmail(email);
        File file = drive.files()
                .get(token)
                .execute();
        String name = file.getName();
        Long size = file.getSize();
        if (null == size) {
            size = 0L;
        }
        System.out.println("Files metadata: name: " + name + " size: " + size);
        DocumentType documentType = DocumentType.builder()
                .id(UUID.randomUUID())
                .name(name)
                .size(size)
                .build();
        documentTypeRepository.saveAndFlush(documentType);
        System.out.println("Document type: " + documentType);
        DirectoryEntity directoryEntity = null;
        if (null == file.getParents()){
            System.out.println("Where are not parent directory of this file: " + file);
        }else {
            if (file.getParents().isEmpty()){
                System.out.println("Where are not parent directory of this file: " + file);
            }else {
                File dir = drive.files().get(file.getParents().get(0)).execute();
                System.out.println("Directory metadata: name: " + dir.getName() + " id: " + dir.getId());
                directoryEntity = DirectoryEntity.builder()
                        .id(UUID.randomUUID())
                        .token(dir.getId())
                        .name(dir.getName())
                        .build();
                System.out.println("Directory entity " + directoryEntity);
                directoryEntityRepository.saveAndFlush(directoryEntity);
            }
        }
        DocumentEntity documentEntity = DocumentEntity.builder()
                .id(UUID.randomUUID())
                .documentType(documentType)
                .owner(userEntity)
                .token(token)
                .parent(directoryEntity)
                .build();
        System.out.println("Document entity: " + documentEntity);
        documentEntityRepository.saveAndFlush(documentEntity);
        return ResponseBody.builder().timestamp(LocalDateTime.now()).message("Files metadata: name: " + name + " size: " + size).build();
    }
}
