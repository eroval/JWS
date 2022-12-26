package com.informatics.CSCB869.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.informatics.CSCB869.data.entity.BaseEntity;

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
}
