package ru.itmo.demoproject.service;

import com.google.api.services.drive.Drive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.demoproject.model.entity.dto.UserRegisterRequestDTO;

@Service
@RequiredArgsConstructor
public class GoogleDriveService {
    public boolean registerUser(UserRegisterRequestDTO userRegisterRequestDTO) {
        return true;
    }

    public Drive getDriveByEmail(String email) {
        return null;
    }
}
