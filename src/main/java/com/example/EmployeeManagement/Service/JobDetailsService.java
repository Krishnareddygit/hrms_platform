package com.example.EmployeeManagement.Service;

import com.example.EmployeeManagement.DTO.JobDetailsDTO;
import com.example.EmployeeManagement.Exception.AccessDeniedException;
import com.example.EmployeeManagement.Exception.DataNotFoundException;
import com.example.EmployeeManagement.Exception.EmployeeNotFoundException;
import com.example.EmployeeManagement.Model.Employee;
import com.example.EmployeeManagement.Model.JobDetails;
import com.example.EmployeeManagement.Repository.EmployeeRepository;
import com.example.EmployeeManagement.Repository.JobDetailsRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class JobDetailsService {

    private JobDetailsRepository jobDetailsRepository;
    private EmployeeRepository employeeRepository;

    // Get job details by jobId
    public JobDetailsDTO getByJobId(Long id) {
        JobDetails job = jobDetailsRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Job details not found with id: " + id));
        return mapToDto(job);
    }

    // Get job details of employee
    public JobDetailsDTO getByEmployeeId(Long employeeId) {
        JobDetails job = jobDetailsRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new DataNotFoundException("Job details not found for employee: " + employeeId));
        return mapToDto(job);
    }

    // Add job details
    public JobDetailsDTO addJobDetails(Long employeeId, JobDetails job) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));

        if (jobDetailsRepository.findByEmployeeId(employeeId).isPresent()) {
            throw new DataNotFoundException("Employee already has job details");
        }

        job.setEmployee(employee);
        job.setCreatedAt(LocalDateTime.now());
        job.setUpdatedAt(LocalDateTime.now());

        return mapToDto(jobDetailsRepository.save(job));
    }

    // Partial update
    @Transactional
    public JobDetailsDTO updateJobDetails(Long jobId, Long employeeId, JobDetailsDTO dto) {

        JobDetails job = jobDetailsRepository.findById(jobId)
                .orElseThrow(() -> new DataNotFoundException("Job details not found with id: " + jobId));

        // Security check
        if (!job.getEmployee().getEmployeeId().equals(employeeId)) {
            throw new AccessDeniedException("Employee is not allowed to modify these job details");
        }

        if (dto.getClientCompany() != null)
            job.setClientCompany(dto.getClientCompany());

        if (dto.getDepartmentName() != null)
            job.setDepartmentName(dto.getDepartmentName());

        if (dto.getBaseLocation() != null)
            job.setBaseLocation(dto.getBaseLocation());

        if (dto.getClientLocation() != null)
            job.setClientLocation(dto.getClientLocation());

        if (dto.getUpdatedBy() != null)
            job.setUpdatedBy(dto.getUpdatedBy());

        job.setUpdatedAt(LocalDateTime.now());

        return mapToDto(job);
    }

    // Delete job details
    public void deleteByEmployeeId(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));

        jobDetailsRepository.deleteByEmployeeId(employeeId);
    }

    // Mapper
    public JobDetailsDTO mapToDto(JobDetails job) {

        JobDetailsDTO dto = new JobDetailsDTO();
        dto.setJobId(job.getJobId());

        if (job.getEmployee() != null)
            dto.setEmployeeId(job.getEmployee().getEmployeeId());

        dto.setClientCompany(job.getClientCompany());
        dto.setDepartmentName(job.getDepartmentName());
        dto.setBaseLocation(job.getBaseLocation());
        dto.setClientLocation(job.getClientLocation());
        dto.setCreatedAt(job.getCreatedAt());
        dto.setUpdatedAt(job.getUpdatedAt());
        dto.setUpdatedBy(job.getUpdatedBy());

        return dto;
    }
}
