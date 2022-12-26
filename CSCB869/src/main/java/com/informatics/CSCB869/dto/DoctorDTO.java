package com.informatics.CSCB869.dto;


import com.informatics.CSCB869.data.entity.Profession;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DoctorDTO {
    private long id;
    private String name;
    private Profession profession;
}