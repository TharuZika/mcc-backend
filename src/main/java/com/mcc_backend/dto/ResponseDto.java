package com.mcc_backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ResponseDto {
    private int status;
    private String message;
    private Object data;
}
