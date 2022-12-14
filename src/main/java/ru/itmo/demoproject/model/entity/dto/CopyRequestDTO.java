package ru.itmo.demoproject.model.entity.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Data
@Builder
@Getter
@ToString
public class CopyRequestDTO {
    private String fileTypeID;
    private String userEmail;
}
