import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

public class CopyGoogleFile {



    public static void main(String... args) throws IOException, GeneralSecurityException {
        File fileMetadata = new File();
        fileMetadata.setName("hello_file");

        List<String> parents = List.of("1E9mQxqrcYm0_1DTvszTGhXsx1YHpyfg2");
        fileMetadata.setParents(parents);

        Drive service = DriveQuickstart.createDriveService();
        service.files().copy("1Pv0VmjVkJIoEDvxkBwWfaAdIiwgQNm2s", fileMetadata).execute();
        System.out.println("Done!");
    }
}
