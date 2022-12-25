package com.informatics.CSCB869.web.view.model;

import javax.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@ToString
public class CreateProfessionViewModel {
    @NotBlank
    @Size(min=1, max=40, message="Min 1, Max 40")
    private String name;
}
