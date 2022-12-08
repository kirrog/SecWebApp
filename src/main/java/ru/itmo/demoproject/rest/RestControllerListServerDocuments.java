package ru.itmo.demoproject.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.demoproject.model.entity.dto.FileDTO;
import ru.itmo.demoproject.service.ListServerFilesService;

import java.util.List;

@RestController
@RequestMapping("api/v1/copy")
@RequiredArgsConstructor
public class RestControllerListServerDocuments {
    private final ListServerFilesService service;

    @GetMapping("")
    public @ResponseBody ResponseEntity<List<FileDTO>> getListOfAvailableDocuments() {
        return ResponseEntity.ok(service.showListOfAvailableFiles());
    }
}
