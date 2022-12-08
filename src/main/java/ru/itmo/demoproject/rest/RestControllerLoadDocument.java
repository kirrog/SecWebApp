package ru.itmo.demoproject.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itmo.demoproject.model.entity.dto.LoadRequestDTO;
import ru.itmo.demoproject.service.LoaderService;

@RestController
@RequestMapping("load")
@RequiredArgsConstructor
public class RestControllerLoadDocument {
    private final LoaderService service;
    @PostMapping("")
    public @ResponseBody ResponseEntity<ru.itmo.demoproject.model.entity.dto.ResponseBody> copyFileToUserDisk(@RequestBody LoadRequestDTO loadRequestDTO) {
        return ResponseEntity.ok(service.loadDocument(loadRequestDTO));
    }
}
