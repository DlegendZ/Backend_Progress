package com.raynald.hireloop.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public interface JobRepository extends JpaRepository<Job, Long> {
    // Spring Data generates this — just know it lives here

    List<Job> findByDepartment(String department);

    @Query("SELECT j FROM Job j LEFT JOIN FETCH j.application WHERE j.id = :id")
    Optional<Job> findByIdWithApplications(@Param("id") Long id);
}
