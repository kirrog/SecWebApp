package ru.itmo.demoproject.model.entity.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class UserRegisterResponseDTO {
    private int status;
    private String email;
}
