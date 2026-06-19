package com.raynald.hireloop.service;

import com.raynald.hireloop.dto.CreateJobRequest;
import com.raynald.hireloop.dto.JobResponse;
import com.raynald.hireloop.dto.UpdateJobRequest;
import com.raynald.hireloop.entity.Job;
import com.raynald.hireloop.exception.ResourceNotFoundException;
import com.raynald.hireloop.mapper.JobMapper;
import com.raynald.hireloop.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;
    private final JobMapper jobMapper;

    public List<JobResponse> getAllJobs() {
        List<Job> jobs = jobRepository.findAll();
        return jobMapper.toResponseList(jobs);
    }

    public JobResponse getJobById(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + id));
        return jobMapper.toResponse(job);
    }

    public List<JobResponse> getJobsByDepartment(String department) {
        List<Job> jobs = jobRepository.findByDepartment(department);
        return jobMapper.toResponseList(jobs);
    }

    public JobResponse createJob(CreateJobRequest request) {
        Job job = jobMapper.toEntity(request);
        Job saved = jobRepository.save(job);
        return jobMapper.toResponse(saved);
    }

    public JobResponse updateJob(Long id, UpdateJobRequest request) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + id));
        jobMapper.updateEntity(job, request);
        Job saved = jobRepository.save(job);
        return jobMapper.toResponse(saved);
    }

    public void deleteJob(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + id));
        jobRepository.delete(job);
    }
}
