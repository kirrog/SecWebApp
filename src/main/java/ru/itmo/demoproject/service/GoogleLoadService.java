package ru.itmo.demoproject.service;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.User;
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
    public ResponseBody loadFileToSystem(String token, String userRedirectLink) throws IOException {
        String code = googleDriveService.getCodeFromLink(userRedirectLink);
        Drive drive = googleDriveService.getDriveByCode(code);
        User user = (User) drive.about()
                .get()
                .setFields("user")
                .execute()
                .get("user");
        String email = user.getEmailAddress();
        UserEntity userEntity = UserEntity.builder().id(UUID.randomUUID()).email(email).code(code).build();
        userEntityRepository.saveAndFlush(userEntity);
        File file = drive.files().get(token).execute();
        DocumentType documentType = DocumentType.builder().id(UUID.randomUUID()).name(file.getName()).size(file.getSize()).build();
        documentTypeRepository.saveAndFlush(documentType);
        File dir = drive.files().get(file.getParents().get(0)).execute();
        DirectoryEntity directoryEntity = DirectoryEntity.builder().id(UUID.randomUUID()).token(dir.getId()).name(dir.getName()).build();
        directoryEntityRepository.saveAndFlush(directoryEntity);
        DocumentEntity documentEntity = DocumentEntity.builder()
                .id(UUID.randomUUID())
                .documentType(documentType)
                .owner(userEntity)
                .token(token)
                .parent(directoryEntity)
                .build();
        documentEntityRepository.saveAndFlush(documentEntity);
        return ResponseBody.builder().build();
    }
}
