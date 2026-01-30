package com.example.EmployeeManagement.Controller;

import com.example.EmployeeManagement.DTO.JobDetailsDTO;
import com.example.EmployeeManagement.Model.JobDetails;
import com.example.EmployeeManagement.Service.JobDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employees")
@AllArgsConstructor
public class JobDetailsController {

    private final JobDetailsService jobDetailsService;

    // Get job details by jobId
    @GetMapping("/jobs/{jobId}")
    public ResponseEntity<JobDetailsDTO> getByJobId(@PathVariable Long jobId) {
        return ResponseEntity.ok(jobDetailsService.getByJobId(jobId));
    }

    // Get job details of employee
    @GetMapping("/{employeeId}/job")
    public ResponseEntity<JobDetailsDTO> getByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(jobDetailsService.getByEmployeeId(employeeId));
    }

    // Add job details
    @PostMapping("/{employeeId}/job")
    public ResponseEntity<JobDetailsDTO> addJobDetails(
            @PathVariable Long employeeId,
            @RequestBody JobDetails job) {

        return ResponseEntity.ok(jobDetailsService.addJobDetails(employeeId, job));
    }

    // Update job details
    @PutMapping("/{employeeId}/job/{jobId}")
    public ResponseEntity<JobDetailsDTO> updateJobDetails(
            @PathVariable Long employeeId,
            @PathVariable Long jobId,
            @RequestBody JobDetailsDTO dto) {

        return ResponseEntity.ok(
                jobDetailsService.updateJobDetails(jobId, employeeId, dto)
        );
    }

    // Delete job details
    @DeleteMapping("/{employeeId}/job")
    public ResponseEntity<Void> deleteByEmployee(@PathVariable Long employeeId) {
        jobDetailsService.deleteByEmployeeId(employeeId);
        return ResponseEntity.noContent().build();
    }
}
