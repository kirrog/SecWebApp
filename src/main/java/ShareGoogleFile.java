import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.Permission;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class ShareGoogleFile {

    // Public a Google File/Folder.
    public static Permission createPublicPermission(String googleFileId) throws IOException, GeneralSecurityException {
        // All values: user - group - domain - anyone
        String permissionType = "anyone";
        // All values: organizer - owner - writer - commenter - reader
        String permissionRole = "reader";

        Permission newPermission = new Permission();
        newPermission.setType(permissionType);
        newPermission.setRole(permissionRole);

        Drive driveService = DriveQuickstart.createDriveService();
        return driveService.permissions().create(googleFileId, newPermission).execute();
    }

    public static Permission createPermissionForEmail(String googleFileId, String googleEmail) throws IOException, GeneralSecurityException {
        // All values: user - group - domain - anyone
        String permissionType = "user"; // Valid: user, group
        // organizer - owner - writer - commenter - reader
        String permissionRole = "reader";

        Permission newPermission = new Permission();
        newPermission.setType(permissionType);
        newPermission.setRole(permissionRole);

        newPermission.setEmailAddress(googleEmail);

        Drive driveService = DriveQuickstart.createDriveService();
        return driveService.permissions().create(googleFileId, newPermission).execute();
    }

    public static void main(String[] args) throws IOException, GeneralSecurityException {

//        String googleFileId1 = "1943UYkXjLloppLAQBJDzx6QAHuCwUYrVDPNf-2wfvwA";
//        String googleEmail = "test.o7planning@gmail.com";
//
//        // Share for a User
//        createPermissionForEmail(googleFileId1, googleEmail);

        String googleFileId2 = "1Pv0VmjVkJIoEDvxkBwWfaAdIiwgQNm2s";

        // Share for everyone
        createPublicPermission(googleFileId2);

        System.out.println("Done!");
    }

}