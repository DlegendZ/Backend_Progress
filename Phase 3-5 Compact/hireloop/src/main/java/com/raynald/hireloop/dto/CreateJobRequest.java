package com.raynald.hireloop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateJobRequest {
//    private String title;
//    private String department;
//    private String location;
//
//    public String getTitle() {return title;}
//    public void setTitle(String title) {this.title = title;}
//
//    public String getDepartment() {return department;}
//    public void setDepartment(String department) {this.department = department;}
//
//    public String getLocation() {return location;}
//    public void setLocation(String location) {this.location = location}

    private String title;
    private String department;
    private String location;
}
