package com.informatics.CSCB869.web.view.model;
import javax.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@ToString
public class CreateDiagnoseViewModel {
    @NotBlank
    @Size(min=1, max=50, message="Min 1, Max 50")
    private String name;
}
