package com.informatics.CSCB869.dto;

import com.informatics.CSCB869.data.entity.Profession;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString

public class CreateDoctorDTO {
    private String name;
    private Profession profession;
}
