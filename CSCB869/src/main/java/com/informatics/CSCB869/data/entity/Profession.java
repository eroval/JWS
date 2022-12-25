package com.informatics.CSCB869.data.entity;
import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "profession",
uniqueConstraints=
@UniqueConstraint(columnNames={"name"})
)
public class Profession extends BaseEntity {
    
    @NotBlank
    @Size(min=1, max=40, message="Min 1, Max 40")
    private String name;

    @OneToMany
    @JsonIgnoreProperties("profession")
    private List<Doctor> doctors;
}