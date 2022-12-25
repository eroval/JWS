package com.informatics.CSCB869.data.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.informatics.CSCB869.data.entity.BaseEntity;

import lombok.*;
import javax.validation.constraints.*;

@Getter
@Setter
@Entity
@Table(name="patient")
public class Patient extends BaseEntity{
    @NotBlank
    @Size(min=1, max=50, message="Min 1, Max 50")
    private String name;

    @NotBlank
    @Size(min=10, max=10, message="Min 10, Max 10")
    private String egn;
}
