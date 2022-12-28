package com.informatics.CSCB869.dto;

import com.informatics.CSCB869.data.entity.Doctor;
import com.informatics.CSCB869.data.entity.Patient;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UpdateDoctorPatientGPDTO {
    private Doctor doctor;
}
