package com.informatics.CSCB869.web.view.model;

import java.time.LocalDate;

import javax.validation.constraints.*;

import org.springframework.format.annotation.DateTimeFormat;

import com.informatics.CSCB869.data.entity.Patient;

import lombok.*;

@Getter
@Setter
@ToString
public class CreateSickLeaveViewModel {
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    
    private Patient patient;
}
