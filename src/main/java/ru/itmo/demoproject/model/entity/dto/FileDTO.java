package ru.itmo.demoproject.model.entity.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Data
@Builder
@Getter
@ToString
public class FileDTO {
    private String name;
    private Long size;
    private UUID id;
}
