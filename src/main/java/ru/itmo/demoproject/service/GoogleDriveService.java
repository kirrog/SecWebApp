package ru.itmo.demoproject.service;

import com.google.api.services.drive.Drive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.demoproject.model.entity.dto.UserRegisterRequestDTO;

@Service
@RequiredArgsConstructor
public class GoogleDriveService {
    public boolean registerUser(UserRegisterRequestDTO userRegisterRequestDTO) {
        // create creditional from code
        // create drive and check ability to do anything (create directory for data)
        // if ok
        //      save code and email to database (check if email already exists)
        // else
        //      return exception with info
        return true;
    }

    public Drive getDriveByEmail(String email) {
        // by email get code
        // create from code creditional and drive
        // if wrong return exception and ask to refresh token
        return null;
    }
}
