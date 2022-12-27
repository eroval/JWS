package com.informatics.CSCB869.data.entity;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;


@Getter
@Setter
@Entity
@Table(name="sickLeave")
public class SickLeave extends BaseEntity {
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @OneToOne
    @JoinColumn(name="patient_id", referencedColumnName = "id")
    private Patient patient;
}
