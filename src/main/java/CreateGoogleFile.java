import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

public class CreateGoogleFile {

    // PRIVATE!
    private static File _createGoogleFile(String googleFolderIdParent, String contentType, //
                                          String customFileName, AbstractInputStreamContent uploadStreamContent) throws IOException, GeneralSecurityException {

        File fileMetadata = new File();
        fileMetadata.setName(customFileName);

        List<String> parents = Arrays.asList(googleFolderIdParent);
        fileMetadata.setParents(parents);
        //
        Drive driveService = DriveQuickstart.createDriveService();

        File file = driveService.files().create(fileMetadata, uploadStreamContent)
                .setFields("id, webContentLink, webViewLink, parents").execute();

        return file;
    }

    // Create Google File from byte[]
    public static File createGoogleFile(String googleFolderIdParent, String contentType, //
                                        String customFileName, byte[] uploadData) throws IOException, GeneralSecurityException {
        //
        AbstractInputStreamContent uploadStreamContent = new ByteArrayContent(contentType, uploadData);
        //
        return _createGoogleFile(googleFolderIdParent, contentType, customFileName, uploadStreamContent);
    }

    // Create Google File from java.io.File
    public static File createGoogleFile(String googleFolderIdParent, String contentType, //
                                        String customFileName, java.io.File uploadFile) throws IOException, GeneralSecurityException {

        //
        AbstractInputStreamContent uploadStreamContent = new FileContent(contentType, uploadFile);
        //
        return _createGoogleFile(googleFolderIdParent, contentType, customFileName, uploadStreamContent);
    }

    // Create Google File from InputStream
    public static File createGoogleFile(String googleFolderIdParent, String contentType, //
                                        String customFileName, InputStream inputStream) throws IOException, GeneralSecurityException {

        //
        AbstractInputStreamContent uploadStreamContent = new InputStreamContent(contentType, inputStream);
        //
        return _createGoogleFile(googleFolderIdParent, contentType, customFileName, uploadStreamContent);
    }

    public static void main(String[] args) throws IOException, GeneralSecurityException {

        java.io.File uploadFile = new java.io.File("/home/kirrog/projects/itmo/sec/SecWebApp/test.txt");

        // Create Google File:

        File googleFile = createGoogleFile(null, "text/plain", "newfile.txt", uploadFile);

        System.out.println("Created Google file!");
        System.out.println("WebContentLink: " + googleFile.getWebContentLink());
        System.out.println("WebViewLink: " + googleFile.getWebViewLink());

        System.out.println("Done!");
    }

}