package com.informatics.CSCB869.web.view.model;
import com.informatics.CSCB869.data.entity.Profession;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class DoctorViewModel {
    private long id;
    private String name;
    private Profession profession;
}
