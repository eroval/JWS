package com.informatics.CSCB869.web.view.model;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.informatics.CSCB869.data.entity.Patient;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class InsuranceViewModel {
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotNull
    private Patient patient;
}
