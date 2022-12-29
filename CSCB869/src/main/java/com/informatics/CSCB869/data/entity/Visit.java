package com.informatics.CSCB869.data.entity;

import lombok.*;

import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.format.annotation.DateTimeFormat;


@Getter
@Setter
@Entity
@Table(name="visit")
public class Visit extends BaseEntity{
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name="doctor_id", referencedColumnName = "id", insertable = true, updatable = false)
    private Doctor doctor;

    @ManyToOne
    @NotNull
    @JoinColumn(name="patient_id", referencedColumnName = "id", insertable = true, updatable = false)
    private Patient patient;
}
