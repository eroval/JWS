package com.informatics.CSCB869.web.view.model;

import lombok.*;
import javax.validation.constraints.*;

import com.informatics.CSCB869.data.entity.Doctor;
import com.informatics.CSCB869.data.entity.Patient;

@Getter
@Setter
@NoArgsConstructor
public class DoctorPatientGPViewModel{
    @NotNull
    private Doctor doctor;

    @NotNull
    private Patient patient;
}