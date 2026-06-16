package com.raynald.hireloop.service;

import com.raynald.hireloop.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepository;

    public Job findById(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new RunTimeException("Job not found"));
    }
}
