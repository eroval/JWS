package com.informatics.CSCB869.data.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.informatics.CSCB869.data.entity.BaseEntity;
import com.informatics.CSCB869.data.entity.PatientDiagnose;

import lombok.*;
import javax.validation.constraints.*;

@Getter
@Setter
@Entity
@Table(name="patient",
uniqueConstraints=@UniqueConstraint
(
    columnNames={"egn"}
)
)
public class Patient extends BaseEntity{
    @NotBlank
    @Size(min=1, max=50, message="Min 1, Max 50")
    private String name;

    @NotBlank
    @Size(min=10, max=10, message="Min 10, Max 10")
    private String egn;


    @OneToMany
    private List<SickLeave> sickleaves; 

    @OneToOne
    private DoctorPatientGP gp;

    @OneToMany
    private List<PatientDiagnose> patientdiagnose;

    @OneToMany
    private List<Visit> visits;

    @OneToMany
    private List<Insurance> insurances;
}
