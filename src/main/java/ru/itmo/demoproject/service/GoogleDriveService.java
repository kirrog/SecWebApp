package ru.itmo.demoproject.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;
import com.google.api.services.drive.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.demoproject.model.entity.DirectoryEntity;
import ru.itmo.demoproject.model.entity.UserEntity;
import ru.itmo.demoproject.model.entity.dto.UserRegisterRequestDTO;
import ru.itmo.demoproject.model.entity.dto.UserRegisterResponseDTO;
import ru.itmo.demoproject.repository.DirectoryEntityRepository;
import ru.itmo.demoproject.repository.UserEntityRepository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class GoogleDriveService {

    private static final List<String> SCOPES =
            Collections.singletonList(DriveScopes.DRIVE);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    //    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final String REDIRECT_URI = "http://localhost:3000/";

    private final UserEntityRepository userEntityRepository;
    private final DirectoryEntityRepository directoryEntityRepository;

    private final Map<String, Drive> driversEmailMap = new HashMap<>();
    private final Map<String, Drive> driversCodeMap = new HashMap<>();

    public void createPublicPermission(Drive drive, String googleFileId) throws IOException {
        // All values: user - group - domain - anyone
        String permissionType = "anyone";
        // All values: organizer - owner - writer - commenter - reader
        String permissionRole = "reader";

        Permission newPermission = new Permission();
        newPermission.setType(permissionType);
        newPermission.setRole(permissionRole);

        drive.permissions().create(googleFileId, newPermission).execute();
    }

    private Drive createDriveFromCode(String code) throws IOException {
        HttpTransport httpTransport = new NetHttpTransport();
        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
        InputStream in = GoogleDriveService.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(jsonFactory, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, jsonFactory, clientSecrets, SCOPES
        ).setAccessType("online").setApprovalPrompt("auto").build();

        flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI).build();
//        String url = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI).build();
//        System.out.println(url);

        GoogleTokenResponse response = flow
                .newTokenRequest(code)
                .setRedirectUri(REDIRECT_URI)
                .execute();

        GoogleCredential credential = new GoogleCredential().setFromTokenResponse(response);
//        DataStore<StoredCredential> credentialDataStore = StoredCredential.getDefaultDataStore(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)));
//        credentialDataStore.set("hello", new StoredCredential(credential));

        return new Drive.Builder(httpTransport, jsonFactory, credential).build();
    }

    public Drive getDriveByCode(String code) throws IOException {
        Drive drive = null;
        try {
            drive = driversCodeMap.get(code);
            drive.files().list().execute();
            return drive;
        } catch (IOException e) {
            System.out.println("Code expired: " + code);
        } catch (NullPointerException e) {
            System.out.println("No element: " + code);
        }
        drive = createDriveFromCode(code);
        User user = (User) drive.about()
                .get()
                .setFields("user")
                .execute()
                .get("user");
        String email = user.getEmailAddress();
        driversEmailMap.put(email, drive);
        driversCodeMap.put(code, drive);
        return drive;
    }

    public Drive getDriveByEmail(String email) {
        return driversEmailMap.getOrDefault(email, null);
    }

    public String getCodeFromLink(String url) {
        String[] splitResult = url.substring(url.indexOf("?") + 1).split("&");
        for (String str : splitResult) {
            if ("code=".equals(str.substring(0, 5))) {
                return str.substring(6);
            }
        }
        throw new NoSuchElementException("Can't find \"code\" in url: " + url + "   " + url.substring(url.indexOf("?") + 1));
    }

    public File createAppRootGoogleFolder(Drive drive, String folderName) throws IOException {
        System.out.println("Try to create folder: " + folderName);
        File fileMetadata = new File();

        fileMetadata.setName(folderName);
        fileMetadata.setMimeType("application/vnd.google-apps.folder");

        return drive.files().create(fileMetadata).setFields("id, name").execute();
    }

    private void saveFolderToDatabase(File directory, UserEntity userEntity) {
        String token = directory.getId();
        String name = directory.getName();
        DirectoryEntity directoryEntity = DirectoryEntity.builder().id(UUID.randomUUID()).token(token).name(name).owner(userEntity).build();
        System.out.println("Directory: " + directoryEntity);
        directoryEntityRepository.saveAndFlush(directoryEntity);
    }

    public UserRegisterResponseDTO registerUser(UserRegisterRequestDTO userRegisterRequestDTO) throws IOException {
//        System.out.println("Link to server: " + userRegisterRequestDTO.getUserRedirectLink());
//        String code = getCodeFromLink(userRegisterRequestDTO.getUserRedirectLink());
        String code = userRegisterRequestDTO.getToken();
        System.out.println("Code getted: " + code);
        Drive drive = getDriveByCode(code);
        User user = (User) drive.about()
                .get()
                .setFields("user")
                .execute()
                .get("user");
        String email = user.getEmailAddress();

        UserEntity userEntity = userEntityRepository.findUserEntityByEmail(email);
        if (userEntity == null) {
            userEntity = UserEntity.builder().id(UUID.randomUUID()).code(code).email(email).build();
            userEntityRepository.saveAndFlush(userEntity);
            File file = createAppRootGoogleFolder(drive, "SecWebAppFolder");
            saveFolderToDatabase(file, userEntity);
        } else {
            userEntity.setCode(code);
            userEntityRepository.saveAndFlush(userEntity);
        }
        return UserRegisterResponseDTO.builder().email(email).status(0).build();
    }
}
