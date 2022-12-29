package com.informatics.CSCB869.data.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.*;
import javax.validation.constraints.*;

@Getter
@Setter
@Entity
@Table(name="doctors")
public class Doctor extends BaseEntity{
    @NotBlank
    @Size(min=1, max=50, message="Min 1, Max 50")
    private String name;

    @ManyToOne
    @NotNull
    @JoinColumn(name="profession_id", referencedColumnName = "id")
    private Profession profession;

    @OneToMany
    private List<DoctorPatientGP> gp;

    @OneToMany
    private List<Visit> visits;
}
