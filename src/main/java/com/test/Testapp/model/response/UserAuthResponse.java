package com.test.Testapp.model.response;


import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAuthResponse {
    private String username;
    private List<String> roles;
}