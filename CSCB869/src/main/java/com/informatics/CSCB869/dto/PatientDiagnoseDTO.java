package com.informatics.CSCB869.dto;

import com.informatics.CSCB869.data.entity.Diagnose;
import com.informatics.CSCB869.data.entity.Doctor;
import com.informatics.CSCB869.data.entity.Patient;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PatientDiagnoseDTO {
    private Patient patient;
    private Diagnose diagnose;
}
