package ru.itmo.demoproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.demoproject.model.entity.dto.FileDTO;
import ru.itmo.demoproject.model.entity.dto.ResponseBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListServerFilesService {
    // collect list from types of documents table in database
    public List<FileDTO> showListOfAvailableFiles() {
        return null;
    }
}
