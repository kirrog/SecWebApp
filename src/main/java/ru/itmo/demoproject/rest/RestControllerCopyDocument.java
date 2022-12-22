package ru.itmo.demoproject.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itmo.demoproject.model.entity.dto.CopyRequestDTO;
import ru.itmo.demoproject.service.CopySevice;

import java.io.IOException;

@RestController
@RequestMapping("copy")
@RequiredArgsConstructor
public class RestControllerCopyDocument {
    private final CopySevice service;
    @CrossOrigin
    @PostMapping("")
    public @ResponseBody ResponseEntity<ru.itmo.demoproject.model.entity.dto.ResponseBody> copyFileToUserDisk(@RequestBody CopyRequestDTO copyRequestDTO) throws IOException {
        return ResponseEntity.ok(service.copyDocument(copyRequestDTO));
    }
}
