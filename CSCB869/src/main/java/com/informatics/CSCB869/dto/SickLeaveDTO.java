package com.informatics.CSCB869.dto;

import java.time.LocalDate;

import com.informatics.CSCB869.data.entity.Patient;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString

public class SickLeaveDTO {
    private long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Patient patient;
}
