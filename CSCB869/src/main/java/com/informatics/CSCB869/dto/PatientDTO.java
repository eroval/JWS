package com.informatics.CSCB869.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PatientDTO {
    private long id;
    private String name;
    private String egn;
}