package com.informatics.CSCB869.web.view.model;

import javax.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@ToString
public class CreateProfessionViewModel {
    @NotBlank
    @Size(min=1, max=50, message="Min 1, Max 50")
    private String name;

    @NotBlank
    @Size(min=10, max=10, message="Min 10, Max 10")
    private String egn;
}
