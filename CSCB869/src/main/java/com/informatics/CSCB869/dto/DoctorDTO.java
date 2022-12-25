package com.informatics.CSCB869.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DoctorDTO {
    private long id;
    private String name;
    private long professionId;
}