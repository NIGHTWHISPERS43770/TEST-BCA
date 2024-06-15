package com.test.Testapp.model.response;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentResponse {
    private String name;
    private String department;
    private int mark;
    private String status;

}