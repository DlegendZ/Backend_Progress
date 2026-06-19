package com.raynald.hireloop.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateJobRequest {

    private String title;
    private String department;
    private String location;
    private String status;
}
