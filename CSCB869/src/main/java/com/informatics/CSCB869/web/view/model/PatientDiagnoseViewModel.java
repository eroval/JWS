package com.informatics.CSCB869.web.view.model;

import javax.validation.constraints.*;

import com.informatics.CSCB869.data.entity.Diagnose;
import com.informatics.CSCB869.data.entity.Patient;

import lombok.*;

@Getter
@Setter
@ToString
public class PatientDiagnoseViewModel {
    
    @NotNull
    private Patient patient;

    @NotNull
    private Diagnose diagnose;

    @NotNull
    private String prescription;
}
