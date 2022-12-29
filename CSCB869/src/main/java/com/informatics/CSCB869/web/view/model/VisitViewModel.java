package com.informatics.CSCB869.web.view.model;

import java.time.LocalDate;

import javax.validation.constraints.*;

import org.springframework.format.annotation.DateTimeFormat;

import com.informatics.CSCB869.data.entity.Doctor;
import com.informatics.CSCB869.data.entity.Patient;

import lombok.*;

@Getter
@Setter
@ToString
public class VisitViewModel {
    private long id;
    private LocalDate date;
    private Doctor doctor;
    private Patient patient;
}
