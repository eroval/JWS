package com.informatics.CSCB869.dto;

import com.informatics.CSCB869.data.entity.Doctor;
import com.informatics.CSCB869.data.entity.Patient;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DoctorPatientGPDTO {
    private Doctor doctor;
    private Patient patient;

    public DoctorPatientGPDTO(Doctor doctor, Patient patient){
        this.doctor = doctor;
        this.patient = patient;
    }
}
