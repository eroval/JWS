package com.informatics.CSCB869.data.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.informatics.CSCB869.data.entity.BaseEntity;

import lombok.*;

@Getter
@Setter
@Entity
@Table(name="doctors")
public class Doctor extends BaseEntity{
    private String name;

    @ManyToOne
    @JoinColumn(name="profession_id")
    private Profession profession;
}
