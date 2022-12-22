package ru.itmo.demoproject.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itmo.demoproject.model.entity.dto.FileDTO;
import ru.itmo.demoproject.service.ListServerFilesService;

import java.util.List;

@RestController
@RequestMapping("files")
@RequiredArgsConstructor
public class RestControllerListServerDocuments {
    private final ListServerFilesService service;

    @CrossOrigin
    @GetMapping("")
    public @ResponseBody ResponseEntity<List<FileDTO>> getListOfAvailableDocuments() {
        return ResponseEntity.ok(service.showListOfAvailableFiles());
    }
}
