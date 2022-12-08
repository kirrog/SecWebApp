package ru.itmo.demoproject.service;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.demoproject.model.entity.dto.UserRegisterRequestDTO;
import ru.itmo.demoproject.model.entity.dto.UserRegisterResponseDTO;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private static final List<String> SCOPES =
            Collections.singletonList(DriveScopes.DRIVE);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    private Drive getDriveByCode(String code) throws IOException {
        HttpTransport httpTransport = new NetHttpTransport();
        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
        InputStream in = RegisterService.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(jsonFactory, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, jsonFactory, clientSecrets, SCOPES
        ).setAccessType("online").setApprovalPrompt("auto").build();

        String redirectUri = "http://localhost:8888/Callback";


        GoogleTokenResponse response = flow
                .newTokenRequest(code)
                .setRedirectUri(redirectUri)
                .execute();

        GoogleCredential credential =
                new GoogleCredential()
                        .setFromTokenResponse(response);
        DataStore<StoredCredential> credentialDataStore = StoredCredential.getDefaultDataStore(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)));
        credentialDataStore.set("hello", new StoredCredential(credential));

        Drive service =
                new Drive.Builder(httpTransport, jsonFactory, credential)
                        .build();
        return service;
    }

    private String getCodeFromUrl(String url) throws NoSuchElementException {
        String[] splitResult = url.split("&");
        for (String str : splitResult) {
            if ("code".equals(str.substring(0, 4))) {
                return str.substring(5);
            }
        }
        throw new NoSuchElementException("Can't find \"code\" in url: " + url);
    }

    public UserRegisterResponseDTO registerUser(UserRegisterRequestDTO userRegisterRequestDTO) {
        String code = getCodeFromUrl(userRegisterRequestDTO.getUserRedirectLink());
        // create drive and save -> other service
        // get email
        // collect info
        // save all and return response as status
        return UserRegisterResponseDTO.builder().build();
    }
}
