package ru.itmo.demoproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.demoproject.model.entity.dto.UserRegisterRequestDTO;
import ru.itmo.demoproject.model.entity.dto.UserRegisterResponseDTO;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final GoogleDriveService service;


    public UserRegisterResponseDTO registerUser(UserRegisterRequestDTO userRegisterRequestDTO) throws GeneralSecurityException, IOException {
        return service.registerUser(userRegisterRequestDTO);
    }
}
