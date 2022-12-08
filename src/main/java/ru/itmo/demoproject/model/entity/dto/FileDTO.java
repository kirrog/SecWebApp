package ru.itmo.demoproject.model.entity.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Builder
@Getter
@ToString
public class FileDTO {
    private UUID id;
    private String name;
    private Long size;
}
