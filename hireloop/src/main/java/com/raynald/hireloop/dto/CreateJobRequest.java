package com.raynald.hireloop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateJobRequest {

    private String title;
    private String department;
    private String Location;
}
