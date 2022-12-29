package com.informatics.CSCB869.web.view.model;

import java.time.LocalDate;

import com.informatics.CSCB869.data.entity.Patient;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class SickLeaveViewModel {
    private long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Patient patient;
}
