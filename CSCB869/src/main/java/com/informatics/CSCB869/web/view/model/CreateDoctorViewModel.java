package com.informatics.CSCB869.web.view.model;

import javax.validation.constraints.*;

import com.informatics.CSCB869.data.entity.Profession;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class CreateDoctorViewModel {
    @NotBlank
    @Size(min=1, max=50, message="Min 1, Max 50")
    private String name;

    @NotNull
    private Profession profession;
}
