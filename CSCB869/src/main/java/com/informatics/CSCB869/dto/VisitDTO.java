package com.informatics.CSCB869.dto;

import java.time.LocalDate;
import com.informatics.CSCB869.data.entity.Doctor;
import com.informatics.CSCB869.data.entity.Patient;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class VisitDTO {
    private long id;
    private LocalDate date;
    private Doctor doctor;
    private Patient patient;
}
