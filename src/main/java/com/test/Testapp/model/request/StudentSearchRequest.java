package com.test.Testapp.model.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentSearchRequest {
    private String id;
    private String name;
    private String status;
    private Integer minMark;
    private Integer maxMark;
    private Integer page;
    private Integer size;


}
