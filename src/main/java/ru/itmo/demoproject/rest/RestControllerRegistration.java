package ru.itmo.demoproject.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itmo.demoproject.model.entity.dto.RegLinkDTO;
import ru.itmo.demoproject.model.entity.dto.UserRegisterRequestDTO;
import ru.itmo.demoproject.model.entity.dto.UserRegisterResponseDTO;
import ru.itmo.demoproject.service.RegisterService;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class RestControllerRegistration {
    private final RegisterService service;

    @CrossOrigin
    @GetMapping("")
    public @ResponseBody ResponseEntity<RegLinkDTO> setBuyOfferToTankSkinByTankTypeId() {
        return ResponseEntity.ok(RegLinkDTO.builder().link("https://accounts.google.com/o/oauth2/auth?access_type=online&approval_prompt=auto&client_id=579109481040-spko98mnhec8oask0t4uapkeflmhb9of.apps.googleusercontent.com&redirect_uri=http://localhost:3000/&response_type=code&scope=https://www.googleapis.com/auth/drive").build());
    }

    @CrossOrigin
    @PostMapping("")
    public @ResponseBody ResponseEntity<UserRegisterResponseDTO> setBuyOfferToTankSkinByTankTypeId(@RequestBody UserRegisterRequestDTO userRegisterRequestDTO) throws GeneralSecurityException, IOException {
        return ResponseEntity.ok(service.registerUser(userRegisterRequestDTO));
    }
}
