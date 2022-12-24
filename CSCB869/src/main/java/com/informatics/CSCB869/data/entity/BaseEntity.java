package com.informatics.CSCB869.data.entity;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
}
