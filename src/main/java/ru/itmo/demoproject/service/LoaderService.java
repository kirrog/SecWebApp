package ru.itmo.demoproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.demoproject.model.entity.dto.LoadRequestDTO;
import ru.itmo.demoproject.model.entity.dto.ResponseBody;

@Service
@RequiredArgsConstructor
public class LoaderService {
    public ResponseBody loadDocument(LoadRequestDTO loadRequestDTO) {
        return ResponseBody.builder().build();
    }
}
