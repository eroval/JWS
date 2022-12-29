package com.informatics.CSCB869.data.entity;

import java.io.Serializable;
import javax.persistence.*;
import lombok.*;
import javax.validation.constraints.*;

import org.springframework.format.annotation.DateTimeFormat;

import com.informatics.CSCB869.data.idclasses.InsuranceId;

import java.time.LocalDate;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
@Table(name="insurance")
@IdClass(InsuranceId.class)
public class Insurance implements Serializable{
    @Id
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Id
    @NotNull
    @Column(name="patient_id")
    private long patientId;

    @ManyToOne
    @JoinColumn(name="patient_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Patient patient;
}
