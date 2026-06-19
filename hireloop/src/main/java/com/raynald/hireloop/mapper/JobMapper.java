package com.raynald.hireloop.mapper;

import com.raynald.hireloop.dto.CreateJobRequest;
import com.raynald.hireloop.dto.JobResponse;
import com.raynald.hireloop.dto.UpdateJobRequest;
import com.raynald.hireloop.entity.Job;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JobMapper {

    public Job toEntity(CreateJobRequest request) {
        return Job.builder()
                .title(request.getTitle())
                .department(request.getDepartment())
                .location(request.getLocation())
                .status("Open")
                .build();
    }

    public void updateEntity(Job job, UpdateJobRequest request) {
        job.setTitle(request.getTitle());
        job.setDepartment(request.getDepartment());
        job.setLocation(request.getLocation());
        job.setStatus(request.getStatus());
    }

    public JobResponse toResponse(Job job) {
        return JobResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .department(job.getDepartment())
                .location(job.getLocation())
                .status(job.getStatus())
                .createdAt(job.getCreatedAt())
                .build();
    }

    public List<JobResponse> toResponseList(List<Job> jobs) {
        return jobs.stream()
                .map(this::toResponse)
                .toList();
    }
}
