package ru.itmo.demoproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.demoproject.model.entity.dto.CopyRequestDTO;
import ru.itmo.demoproject.model.entity.dto.ResponseBody;

@Service
@RequiredArgsConstructor
public class CopySevice {


    public ResponseBody copyDocument(CopyRequestDTO copyRequestDTO) {
        return ResponseBody.builder().build();
    }
}
