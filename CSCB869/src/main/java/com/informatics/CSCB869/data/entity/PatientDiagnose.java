package com.informatics.CSCB869.data.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.informatics.CSCB869.data.idclasses.PatientDiagnoseId;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "patient_diagnose")
@IdClass(PatientDiagnoseId.class)
public class PatientDiagnose implements Serializable{
    @Id
    @NotNull
    @Column(name="patient_id")
    private long patientId;

    @Id
    @NotNull
    @Column(name="diagnose_id")
    private long diagnoseId;
    
    @NotNull
    @Column(name="prescription")
    private String prescription;

    @ManyToOne
    @JoinColumn(name="patient_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name="diagnose_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Diagnose diagnose;
    
}
