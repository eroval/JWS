package com.informatics.CSCB869.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Where(clause = "deleted = 0")
@Table(name = "school")
public class Profession extends BaseEntity {
    private String name;
}