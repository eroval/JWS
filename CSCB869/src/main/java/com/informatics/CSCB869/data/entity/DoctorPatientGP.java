package com.informatics.CSCB869.data.entity;

import java.io.Serializable;

import javax.persistence.*;

import lombok.*;
import javax.validation.constraints.*;

import com.informatics.CSCB869.data.idclasses.DoctorPatientGPId;

@Getter
@Setter
@Entity
@Table(name="doctor_patient_GP")
@IdClass(DoctorPatientGPId.class)
public class DoctorPatientGP implements Serializable{ 
    @Id
    @NotNull
    @Column(name="doctor_id")
    private long doctorId;

    @Id
    @NotNull
    @Column(name="patient_id")
    private long patientId;

    @ManyToOne
    @JoinColumn(name="doctor_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Doctor doctor;

    @OneToOne
    @JoinColumn(name="patient_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Patient patient;
}
