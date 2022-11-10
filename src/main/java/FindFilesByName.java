import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class FindFilesByName {

    // com.google.api.services.drive.model.File
    public static final List<File> getGoogleFilesByName(String fileNameLike) throws IOException, GeneralSecurityException {

        Drive driveService = DriveQuickstart.createDriveService();

        String pageToken = null;
        List<File> list = new ArrayList<File>();

        String query = " name contains '" + fileNameLike + "' " //
                + " and mimeType != 'application/vnd.google-apps.folder' ";

        do {
            FileList result = driveService.files().list().setQ(query).setSpaces("drive") //
                    // Fields will be assigned values: id, name, createdTime, mimeType
                    .setFields("nextPageToken, files(id, name, createdTime, mimeType)")//
                    .setPageToken(pageToken).execute();
            for (File file : result.getFiles()) {
                list.add(file);
            }
            pageToken = result.getNextPageToken();
        } while (pageToken != null);
        //
        return list;
    }

    public static void main(String[] args) throws IOException, GeneralSecurityException {

        List<File> rootGoogleFolders = getGoogleFilesByName("test_");
        for (File folder : rootGoogleFolders) {

            System.out.println("Mime Type: " + folder.getMimeType() + " --- Name: " + folder.getName() + " --- Id: " + folder.getId());
        }

        System.out.println("Done!");
    }

}