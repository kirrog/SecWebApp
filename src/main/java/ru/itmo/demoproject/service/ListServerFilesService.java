package ru.itmo.demoproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.demoproject.model.entity.DocumentType;
import ru.itmo.demoproject.model.entity.dto.FileDTO;
import ru.itmo.demoproject.repository.DocumentTypeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ListServerFilesService {
    // collect list from types of documents table in database
    private final DocumentTypeRepository documentTypeRepository;

    public List<FileDTO> showListOfAvailableFiles() {
        List<DocumentType> documentTypeList = documentTypeRepository.findAll();
        List<FileDTO> fileDTOList = new ArrayList<>(documentTypeList.size());
        for (DocumentType documentType : documentTypeList) {
            fileDTOList.add(
                    FileDTO.builder()
                            .name(documentType.getName())
                            .size(documentType.getSize())
                            .id(documentType.getId())
                            .build()
            );
        }
        return fileDTOList;
    }
}
