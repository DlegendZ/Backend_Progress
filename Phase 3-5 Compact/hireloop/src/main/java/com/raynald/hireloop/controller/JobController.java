package com.raynald.hireloop.controller;

import com.raynald.hireloop.dto.CreateJobRequest;
import org.apache.coyote.Response;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {
//    @GetMapping
//    public List<Job> getAllJobs() {
//        return List.of();
//    }
//
//    @GetMapping("/active")
//    public List<Job> getActiveJobs() {
//        return List.of();
//    }
//
//    @GetMapping("/{id}")
//    public Job getJobById(@PathVariable Long id) {
//        return jobService.findById(id);
//    }
//
//    @GetMapping("/{jobId}/candidates/{candidateId}")
//    public Candidate getCandidate(
//            @PathVariable Long jobId,
//            @PathVariable Long candidateId
//    ) {
//        return candidateService.findByJobAndId(jobId, candidateId)
//    }
//
//    @GetMapping("/{job_id}")
//    public Job getJob(@PathVariable("job_id") Long id) {
//        return jobService.findById(id);
//    }
//
//    @PostMapping
//    public Job createJob(@RequestBody CreateJobRequest request) {
//        return jobService.create(request);
//    }

    @GetMapping
    public ResponseEntity<list<JobResponse>> getAllJobs() {
        List<JobResponse> jobs = List.of();
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobResponse> getJobById(@PathVariable Long id) {
        JobResponse job = JobResponse.builder()
                .id(id)
                .title("Backend Engineer")
                .department("Engineering")
                .status("PUBLISHED")
                .build();
        return ResponseEntity.ok(job);
    }

    @PostMapping
    public ResponseEntity<JobResponse> createJob(@RequestBody CreateJobRequest request) {
        JobResponse created = JobResponse.builder()
                .id(1L)
                .title(request.getTitle())
                .department(request.getDepartment())
                .status("DRAFT")
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobResponse> updateJob(
            @PathVariable Long id,
            @RequestBody CreateJobRequest request
    ) {
        JobResponse updated = JobResponse.builder()
                .id(id)
                .title(request.getTitle())
                .department(request.getDepartment())
                .status("PUBLISHED")
                .build();
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<void> deleteJob(@PathVariable Long id) {
        return ResponseEntity.noContent().build();
    }
}
