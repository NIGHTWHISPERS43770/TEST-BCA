package com.test.Testapp.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAuthRequest {
    @NotBlank(message = "MUST NOT BLANK")
    private String username;
    @NotBlank(message = "MUST NOT BLANK")
    private String password;
}
