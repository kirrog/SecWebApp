package ru.itmo.demoproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.demoproject.model.entity.dto.LoadRequestDTO;
import ru.itmo.demoproject.model.entity.dto.ResponseBody;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class LoaderService {
    // get share link to file, and get the user id and make request to google loader service
    private final GoogleLoadService service;

    public String getFileTokenFromLink(String link) {
        return link.substring(32).split("/")[0];
    }

    public ResponseBody loadDocument(LoadRequestDTO loadRequestDTO) throws IOException {
        return service.loadFileToSystem(getFileTokenFromLink(loadRequestDTO.getUrl()), loadRequestDTO.getUserRedirectLink());
    }
}
