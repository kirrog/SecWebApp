package ru.itmo.demoproject.model.entity.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class RegLinkDTO {
    private String link;
}